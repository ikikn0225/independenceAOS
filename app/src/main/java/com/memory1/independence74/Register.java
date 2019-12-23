package com.memory1.independence74;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.memory1.independence74.data.UserInfoData;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private ArrayList<UserInfoData> arrayList_Inde;
    private EditText user_id;
    private EditText user_pw;
    private EditText user_pwchecker;
    private EditText user_name;
    private ImageView user_image;
    private Uri photo_uri;
    private Button btn_being_user;
    private Bitmap bitmap;
    private String compare_email = "";
    private String compare_name = "";

    private File tempFile;
    private final int REQUEST_IMAGE_GALLERY = 100;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private StorageTask storageTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        getSupportActionBar().setTitle("회원가입");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 파이어베이스 아이디 확인
        mAuth = FirebaseAuth.getInstance();

        //파이어베이스 데이터베이스 인스턴스 선언
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference refDatabase = databaseReference1.child("user_info");
        databaseReference = FirebaseDatabase.getInstance().getReference("user_info");
        storageReference = FirebaseStorage.getInstance().getReference("user_info");

        //array 없으면
        if (arrayList_Inde == null) {
            arrayList_Inde = new ArrayList<>();
        }

        user_id = findViewById(R.id.user_email);
        user_pw = findViewById(R.id.user_password);
        user_pwchecker = findViewById(R.id.user_passwordcheck);
        user_name = findViewById(R.id.user_name);
        user_image = findViewById(R.id.user_image);

        btn_being_user = findViewById(R.id.btn_being_user);

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(iGallery, REQUEST_IMAGE_GALLERY);
                Log.e("user_image", "" + iGallery);
            }
        });

        //가입완료 버튼 클릭 시
        btn_being_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ((EditText) findViewById(R.id.user_email)).getText().toString();
                final String password = ((EditText) findViewById(R.id.user_password)).getText().toString();
                final String password_checker = ((EditText) findViewById(R.id.user_passwordcheck)).getText().toString();
                final String name = ((EditText) findViewById(R.id.user_name)).getText().toString();
                //비번패턴체크
                String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$";
                final Boolean pw = Pattern.matches(pwPattern,password);

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                            String msg = ds.child("str_user_name").getValue(String.class);
//                            Log.e("msg", "" + msg);
//                        }
                        //초기화
                        compare_email = "";
                        compare_name = "";
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            String msg = ds.getKey();
                            String msg2 = ds.child("str_user_name").getValue(String.class);

                            //아이디 비교
                            if(msg.equals(email)) {
                                compare_email = msg;
                                Log.e("compare", "" + compare_email);
                            }
                            //이름 비교
                            if(msg2.equals(name)) {
                                compare_name = msg2;
                                Log.e("comparename", "" + compare_name);
                            }
                        }
                            //파베의 이메일 값과 입력한 이메일 값이 같으면 생성 못한다고 토스트 띄우기
                            //파베의 이메일 값과 입력한 이메일 값이 다르면 생성 가능하여 if문 통과시키기
                        Log.e("compare", "" + compare_email);
                        if(compare_email.equals("")) {
                            if (email.length() > 6 && email.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝| ]*")) {
                                if (pw) {
                                    if (password.equals(password_checker)) {
                                        //이름의 길이가 0 이상이면
                                        //같은 이름이 없는지 확인
                                        if (name.length() > 0) {
                                            if (compare_name.equals("")) {
                                                if (storageTask != null) {
                                                    Toast.makeText(Register.this, "회원가입 중입니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    uploadFile();
                                                    Log.e("upload", "성공");
                                                    Toast.makeText(Register.this, "회원가입을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                                    gotoActivity(Login.class);
                                                }
                                                //이름이 존재할때
                                            } else {
                                                Toast.makeText(Register.this, "존재하는 이름(닉네임)입니다.", Toast.LENGTH_SHORT).show();
                                            }
                                            } else {
                                                //이름 형식이 아닐때
                                                Toast.makeText(Register.this, "이름(닉네임)을 지어주세요.", Toast.LENGTH_SHORT).show();
                                            }

                                    } else {
                                        //비밀번호가 다를때
                                        Toast.makeText(Register.this, "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    //비밀번호 형식이 아닐떄
                                    Toast.makeText(Register.this, "비밀번호 형식을 지켜주세요.", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                //이메일 형식이 아닐때
                                Toast.makeText(Register.this, "특수 문자를 제외한 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(Register.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                refDatabase.addListenerForSingleValueEvent(eventListener);
            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        Log.e("성공", "성공");
        if(photo_uri != null) {
            Log.e("성공", "성공");
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
            +"."+getFileExtension(photo_uri));
            Log.e("성공", "성공");
            fileReference.putFile(photo_uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    Log.e("Photo", "성공");
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Log.e("task", "성공");
                        final String email = user_id.getText().toString();
                        Uri downloadUri = task.getResult();
                        UserInfoData userInfoData = new UserInfoData(email, user_pw.getText().toString(),
                                user_name.getText().toString(), downloadUri.toString());
                        databaseReference.child(email).setValue(userInfoData);
                    } else {
                        Toast.makeText(Register.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else {
            Toast.makeText(this, "사진이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
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
            Picasso.get().load(photo_uri).into(user_image);
        }
    }


}
