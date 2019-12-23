package com.memory1.independence74;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.memory1.independence74.data.UserInfoData;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UserInfoChange extends AppCompatActivity {

    private ArrayList<UserInfoData> arrayList_Inde;
    private ImageView setting_user_image;
    private TextView setting_user_email;
    private TextView setting_user_name;
    private EditText setting_user_current_pw;
    private EditText setting_user_new_pw;
    private EditText setting_user_new_pw_checker;
    private Button setting_btn_userinfo;
    private ImageView progress_image;
    private ProgressBar progress_login;
    private String compare_name;
    private Handler handler = new Handler();

    private final int REQUEST_IMAGE_GALLERY = 100;
    private Uri photo_uri;
    private File tempFile;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    StorageReference storageReference;
    private StorageTask storageTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_change);

        getSupportActionBar().setTitle("회원 정보 수정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("user_info");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("user_info");
        storageReference = FirebaseStorage.getInstance().getReference("user_info");

        setting_user_image = findViewById(R.id.setting_user_image);
        setting_user_email = findViewById(R.id.setting_user_email);
        setting_user_name = findViewById(R.id.setting_user_name);
        setting_user_current_pw = findViewById(R.id.setting_user_current_pw);
        setting_user_new_pw = findViewById(R.id.setting_user_new_pw);
        setting_user_new_pw_checker = findViewById(R.id.setting_user_new_pw_checker);
        setting_btn_userinfo = findViewById(R.id.setting_btn_userinfo);

        progress_image = findViewById(R.id.progress_image);
        progress_login = findViewById(R.id.progress_login);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        final String login_state = sharedPreferences.getString("email", null);

        //사용자 설정 정보 가져와서 상단에 뿌리기(이미지, 이메일, 이름)
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(login_state).exists()) {
                    //파베 데이터 불러오기
                    UserInfoData userInfoData = dataSnapshot.child(login_state).getValue(UserInfoData.class);
                    setting_user_email.setText(userInfoData.getStr_user_id());
                    setting_user_name.setText(userInfoData.getStr_user_name());
                    Glide.with(getApplicationContext()).load(userInfoData.getStr_user_image()).into(setting_user_image);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //이미지 버튼 클릭 시
        setting_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(iGallery, REQUEST_IMAGE_GALLERY);
            }
        });
        Log.e("login3", "login");

        //저장 버튼 클릭 시
        setting_btn_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        String login_state1 = sharedPreferences.getString("email", null);
                        Log.e("login2", "login");

                        if(login_state1 != null) {
                            Log.e("login1", "login");
                            //shared에 현재 비밀번호가 맞는지 틀린지 검사 후 아니면 토스트,
                            // 맞으면 새로운 비밀번호 입력 null 값 확인 후 아니면 토스트,
                            // 새로운 비밀번호 중복 검사 확인 후 아니면 토스트
                            //gotoActivity to UserSetting.class
                            String current_pw = ((EditText) findViewById(R.id.setting_user_current_pw)).getText().toString();
                            String password = ((EditText) findViewById(R.id.setting_user_new_pw)).getText().toString();
                            String password_checker = ((EditText) findViewById(R.id.setting_user_new_pw_checker)).getText().toString();
                            String name = ((EditText) findViewById(R.id.setting_user_name)).getText().toString();
                            String saved_current_pw = "";

                            //비번패턴체크
                            String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";

                            //파베 데이터 불러오기
                            UserInfoData userInfoData = dataSnapshot.child(login_state).getValue(UserInfoData.class);

                            //기존 비밀번호 불러오기
                            Boolean pw = Pattern.matches(pwPattern, password);
                            saved_current_pw = userInfoData.getStr_user_pw();
                            Log.e("login", "login");

                            //초기화
                            compare_name = "";
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                String msg = ds.child("str_user_name").getValue(String.class);
                                String msg2 = ds.getKey();

                                //이름 비교
                                if(msg.equals(name) && !login_state1.equals(msg2)) {
                                    compare_name = msg;
                                    Log.e("comparename", "" + compare_name);
                                }
                            }

                            //입력한 current_pw가 기존의 pw와 대조 검사
                            if(current_pw.equals(saved_current_pw)) {
                                //닉네임 수정 시
                                if (name.length() > 0) {
                                    if(compare_name.equals("")) {
                                        //비밀번호 수정 시
                                        if (pw) {
                                            if (password.equals(password_checker) && !password.equals("")) {

                                                //user정보 저장
                                                if (storageTask != null) {
                                                    Toast.makeText(UserInfoChange.this, "회원가입 중입니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    uploadFile();

                                                    //로그인 체크
                                                    SharedPreferences sharedPreferences1 = getSharedPreferences("userInfo", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                                                    editor.putBoolean("logine_check", false);
                                                    editor.apply();

                                                    Log.e("비번", "ㅇㅇ");
                                                    Toast.makeText(UserInfoChange.this, "정보를 수정하였습니다.", Toast.LENGTH_SHORT).show();
                                                }

                                            } else {
                                                //비밀번호가 다를때
                                                Log.e("비번1", "ㅇㅇ");
                                                Toast.makeText(UserInfoChange.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            //비밀번호 형식이 아닐떄
                                            Log.e("비번2", "ㅇㅇ");
                                            Toast.makeText(UserInfoChange.this, "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(UserInfoChange.this, "존재하는 이름(닉네임)입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //이름 형식이 아닐때
                                    Log.e("비번3", "ㅇㅇ");
                                    Toast.makeText(UserInfoChange.this, "이름(닉네임)을 지어주세요.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //기존 비밀번호와 입력한 현재 비밀번호가 다를때
                                Log.e("비번4", "ㅇㅇ");
                                Toast.makeText(UserInfoChange.this, "현재 비밀번호를 먼저 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });
        }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("uploadFile", "성공");
                final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                String login_state = sharedPreferences.getString("email", null);
                if(login_state != null) {

                    if (photo_uri != null) {
                        Log.e("photo_uri", "성공");
                        final UserInfoData userInfoData = dataSnapshot.child(login_state).getValue(UserInfoData.class);

                        final StorageReference fileReference1 = storageReference.child(System.currentTimeMillis()
                                + "." + getFileExtension(photo_uri));
                        Log.e("와우2", "성공");

                        fileReference1.putFile(photo_uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    Log.e("와우", "성공");
                                    throw task.getException();
                                }
                                Log.e("와우1", "성공");
                                return fileReference1.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Log.e("성공", "성공");
                                    //로그아웃 시키고 새로 로그인 시키기

                                    Uri downloadUri = task.getResult();
                                    UserInfoData userInfoData1 = new UserInfoData(userInfoData.getStr_user_id(), setting_user_new_pw.getText().toString(),
                                            setting_user_name.getText().toString(), downloadUri.toString());

                                    databaseReference.child(userInfoData.getStr_user_id()).setValue(userInfoData1);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", null);
                                    editor.putString(userInfoData.getStr_user_id(), null);
                                    editor.apply();
                                    gotoActivity(MainActivity.class);
                                } else {
                                    Toast.makeText(UserInfoChange.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {

                    SharedPreferences sharedPreferences1 = getSharedPreferences("userInfo", MODE_PRIVATE);
                    String login_state2 = sharedPreferences1.getString("email", null);
                    if(login_state2 != null) {

                        UserInfoData userInfoData1 = dataSnapshot.child(login_state).getValue(UserInfoData.class);
                        UserInfoData userInfoData = new UserInfoData(login_state, setting_user_new_pw.getText().toString(),
                                setting_user_name.getText().toString(), userInfoData1.getStr_user_image());
                        databaseReference.child(login_state2).setValue(userInfoData);
                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                        editor.putString("email", null);
                        editor.putString(userInfoData.getStr_user_id(), null);
                        editor.apply();

                        gotoActivity(MainActivity.class);
                    }



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //예외 처리 : 갤러리 선택 후 사진 선택 하지 않은 경우
        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "사진 선택이 취소되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        tempFile = null;
                    }
                }
            }

            return;
        }
        //이미지 선택 실행
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                photo_uri = data.getData();
                Cursor cursor = null;
                try {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    assert photo_uri != null;
                    cursor = getContentResolver().query(photo_uri, proj, null, null, null);
                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    tempFile = new File(cursor.getString(column_index));
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
                Glide.with(UserInfoChange.this).load(photo_uri).into(setting_user_image);
//                setting_user_image.setImageURI(photo_uri);
            }

        }


    private void gotoActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}


