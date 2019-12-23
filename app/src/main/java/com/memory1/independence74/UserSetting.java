package com.memory1.independence74;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.memory1.independence74.data.UserInfoData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserSetting extends AppCompatActivity {

    private TextView user_info_change;
    private TextView user_out;
    private ImageView user_image;
    private TextView user_name;

    DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_layout);

        getSupportActionBar().setTitle("사용자 설정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //파이어베이스 데이터베이스 인스턴스 선언
        databaseReference = FirebaseDatabase.getInstance().getReference("user_info");

        user_info_change = findViewById(R.id.user_info_change);
        user_out = findViewById(R.id.user_out);
        user_image = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);

        //사용자 설정 정보 가져와서 상단에 뿌리기(이미지, 이름)
        //파베 디비 userInfo에서 현재 접속 사용자 정보 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        final String email_key = sharedPreferences.getString("email", null);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(email_key).exists()) {
                        UserInfoData userInfoData = dataSnapshot.child(email_key).getValue(UserInfoData.class);
                        user_name.setText(userInfoData.getStr_user_name());
//                        Picasso.get().load(userInfoData.getStr_user_image()).into(user_image);
                        Glide.with(UserSetting.this).load(userInfoData.getStr_user_image()).into(user_image);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//        String email_key = sharedPreferences.getString("email", null);
//        String user_info = sharedPreferences.getString(email_key, null);
//        try {
//            JSONArray jsonArray = new JSONArray(user_info);
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//            user_image.setImageURI(Uri.parse(jsonObject.getString("imageUri")));
//            user_name.setText(jsonObject.getString("name"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        //회원 정보 수정 이동
        user_info_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                final String login_state2 = sharedPreferences.getString("email", null);

                if (login_state2 == null) {
                    //게스트이므로 수정 할 수 없다는 다이얼로그 띄운다.
                    final AlertDialog.Builder logout_dialog = new AlertDialog.Builder(UserSetting.this);
                    logout_dialog.setTitle("회원 수정 불가");
                    logout_dialog.setMessage("회원이 아니므로 수정이 불가합니다.");
                    logout_dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    logout_dialog.show();

                } else {
                    gotoActivity(UserInfoChange.class);
                }
            }
        });

        user_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                final String login_state2 = sharedPreferences.getString("email", null);

                if (login_state2 == null) {
                    //게스트이므로 수정 할 수 없다는 다이얼로그 띄운다.
                    final AlertDialog.Builder logout_dialog = new AlertDialog.Builder(UserSetting.this);
                    logout_dialog.setTitle("회원 탈퇴 불가");
                    logout_dialog.setMessage("회원이 아니므로 탈퇴가 불가합니다.");
                    logout_dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    logout_dialog.show();

                } else {
                    //회원탈퇴 확인 다이얼로그
                    final AlertDialog.Builder logout_dialog = new AlertDialog.Builder(UserSetting.this);
                    logout_dialog.setTitle("회원 탈퇴");
                    logout_dialog.setMessage("회원 탈퇴 하시겠습니까?");
                    logout_dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //realTime 회원 데이터 삭제
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.child(login_state2).exists()) {
                                        gotoActivity(Login.class);

                                        databaseReference.child(login_state2).setValue(null);
                                        editor.putString("email", null);
                                        editor.putString(login_state2, null);
                                        editor.apply();
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    });
                    logout_dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    logout_dialog.show();
                }
            }
        });





    }

    public void gotoActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
