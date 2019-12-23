package com.memory1.independence74;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.memory1.independence74.data.UserInfoData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Login extends AppCompatActivity {

    public static final String EMAIL = "com.example.independence74";

    private TextView text_74;
    private EditText user_email;
    private EditText user_pw;
    private Button btn_login;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        getSupportActionBar().setTitle("로그인");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text_74 = findViewById(R.id.text_74);
        user_email = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_password);
        btn_login = findViewById(R.id.btn_login);


        // 파이어베이스 아이디 확인
        mAuth = FirebaseAuth.getInstance();

        //파이어베이스 데이터베이스 인스턴스 선언
        databaseReference1 = FirebaseDatabase.getInstance().getReference("user_info");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", user_email.getText().toString());
                editor.apply();

                //shared에 아이디 불러오기
                String email_key = sharedPreferences.getString("email", null);
                Log.e("email_key", "" + email_key);

                //아이디 있는지 확인
                //-회원정보 수정할때 Login, Main을 훑고 확인 하기 때문에
                //-회원정보 수정 시 shared의 회원 정보를 삭제하고 그 여부를 확인하는
                //-조건을 하나 더 부여함
                if (email_key != null) {
                    //파이어베이스에 디비에 저장된 정보 불러와서 확인
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //아이디가 존재한다면
                            if (dataSnapshot.child(user_email.getText().toString()).exists()) {
                                //shared에 아이디 넣기

                                Log.e("kkk", "kkk");
                                //파베 데이터 불러오기
                                UserInfoData userInfoData = dataSnapshot.child(user_email.getText().toString()).getValue(UserInfoData.class);
                                //파베 비번과 입력 비번 체크

                                    if (userInfoData.getStr_user_pw().equals(user_pw.getText().toString())) {
                                        //쉐어드에 저장하기
                                        Log.e("hhh", "kkhhhk");

                                        //이메일값 MainActivity로 넘겨주기
                                        String navi_email_key = user_email.getText().toString();
                                        Log.e("email", "" + navi_email_key);
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        intent.putExtra(EMAIL, navi_email_key);

                                        //푸시 알림
                                        //나중에 푸시 알림 스위치 조건에 따라 메세지 보낼지 말지 정하기
                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                                                    @Override
                                                    public void onSuccess(InstanceIdResult instanceIdResult) {
                                                        String newToken = instanceIdResult.getToken();
                                                        Log.e("newToken", newToken);
                                                    }
                                                });

                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Log.e("헤ㅔㅎ", "헤헤");
                                        Toast.makeText(Login.this, "비밀번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Login.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

}
