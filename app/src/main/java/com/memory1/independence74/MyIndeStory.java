package com.memory1.independence74;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.memory1.independence74.adapter.MyStoryAdapter;
import com.memory1.independence74.data.MyStoryData;
import com.memory1.independence74.data.UserInfoData;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyIndeStory extends AppCompatActivity {

    private ArrayList<MyStoryData> myStoryDataArrayList;
    private ArrayList<UserInfoData> userInfoDataArrayList;
    private MyStoryAdapter myStoryAdapter;
    private RecyclerView mystoryRv;
    private LinearLayoutManager linearLayoutManager;

    private RelativeLayout floating_layout;
    private ImageView floating_user_image;
    private TextView floating_user_name;
    private ImageView floating_image;
    private EditText floating_text;
    private Button floating_btn;
    private final int REQUEST_IMAGE_GALLERY = 1, REQUEST_CAMERA = 2, REQUEST_EDIT_IMAGE_GALLERY = 3, REQUEST_EDIT_CAMERA = 4;
    private Uri photoUri;
    private Bitmap originalBm;
    File tempFile;
    private String imageFilePath;
    private String mCurrentPhotoPath;
    private ImageView imageView;
    private Bitmap bitmap;

    //컨텍스트 버튼
    private ImageView mystory_more_vert;

    //플로팅 내부 user_text
    private String user_text;

    //logined name을 저장하기 위한 변수
    private String logined_name;


    //현재 날짜 변수
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd(kk:mm:ss)");

    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    StorageReference storageReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference myStoryRef = db.collection("MyStory");

    //댓글 액티비티로 TEXT 값 넘기기
    public static final String POSITION = "com.example.solobob";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_inde_story_main_layout);

        getSupportActionBar().setTitle("나의 독립 이야기");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //파이어베이스 데이터베이스 인스턴스 선언
        databaseReference = FirebaseDatabase.getInstance().getReference("user_info");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("my_story");
        storageReference = FirebaseStorage.getInstance().getReference("my_story");

        //플로팅 버튼 클릭 시
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_btnforadding);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(MyIndeStory.this);
                View v = LayoutInflater.from(MyIndeStory.this).inflate(R.layout.my_inde_story_dialog, null);
                alert.setView(v);

                tedPermission();

                floating_layout = v.findViewById(R.id.mystroy_floating_image_layout);
                floating_user_image = v.findViewById(R.id.mystory_floating_user_image);
                floating_user_name = v.findViewById(R.id.mystroy_floating_user_name);
                floating_image = v.findViewById(R.id.mystory_floating_image);
                floating_text = v.findViewById(R.id.mystory_floating_text);
                floating_btn = v.findViewById(R.id.mystroy_floating_btn);

                //파베 사용자 데이터를 플로팅 버튼 위에 사용자 정보 표시하기
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        String email_key = sharedPreferences.getString("email", null);
                        Log.e("email_key", "" + email_key);

                        if(dataSnapshot.child(email_key).exists()) {
                            //파베 데이터 불러오기
                            UserInfoData userInfoData = dataSnapshot.child(email_key).getValue(UserInfoData.class);
                            Log.e("email_key1", "" + email_key);

                            Log.e("userInfo", "" + userInfoData);
                            Glide.with(MyIndeStory.this).load(userInfoData.getStr_user_image()).into(floating_user_image);
                            floating_user_name.setText(userInfoData.getStr_user_name());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //아래는 다이얼로그 안의 구성을 받고, 다이얼로그를 생성한다.
                //다이얼로그 생성한다.
                final AlertDialog alertDialog = alert.create();

                //이미지 클릭 시
                floating_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //카메라, 갤러리 선택 다이얼로그 실행
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyIndeStory.this);

                        builder1.setTitle("사진 업로드");

                        builder1.setItems(R.array.camera, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int pos)
                            {
                                String[] items = getResources().getStringArray(R.array.camera);
                                if(items[pos].equals("카메라")) {
                                    //카메라 실행
                                    takePhoto();

                                }else if(items[pos].equals("갤러리")) {
                                    //갤러리 실행
                                    Intent iGallery = new Intent(Intent.ACTION_PICK);
                                    iGallery.setType(MediaStore.Images.Media.CONTENT_TYPE);
                                    startActivityForResult(iGallery, REQUEST_IMAGE_GALLERY);
                                }

                            }
                        });
                        AlertDialog alertDialog1 = builder1.create();
                        alertDialog1.show();

                        //등록 버튼 클릭 시
                        floating_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //현재 날짜 가져오는 메서드 실행
                                getTime();

                                //리사이클러뷰에 기존에 있던 데이터 + 입력한 데이터 같이 뿌려주기
                                //기존에 있던 데이터를 shared에서 불러와서 넣어주고, 입력한 데이터와 같이 데이터에 넣어준다.
                                user_text = floating_text.getText().toString();

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                                        String email_key = sharedPreferences.getString("email", null);
                                        if(dataSnapshot.child(email_key).exists()) {

                                            if(photoUri != null) {
                                                final UserInfoData userInfoData = dataSnapshot.child(email_key).getValue(UserInfoData.class);

                                                final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                                                        +"."+getFileExtension(photoUri));

                                                fileReference.putFile(photoUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                    @Override
                                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                        if (!task.isSuccessful()) {
                                                            throw task.getException();
                                                        }
                                                        return fileReference.getDownloadUrl();
                                                    }
                                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Uri> task) {
                                                        if (task.isSuccessful()) {
                                                            Uri downloadUri = task.getResult();

                                                            CollectionReference collectionReference = FirebaseFirestore.getInstance()
                                                                    .collection("MyStory");
                                                            collectionReference.add(new MyStoryData(userInfoData.getStr_user_image(), userInfoData.getStr_user_name(),
                                                                    getTime(), downloadUri.toString(), userInfoData.getStr_user_name(), user_text));
                                                        } else {
                                                            Toast.makeText(MyIndeStory.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

//            storageTask = fileReference.putFile(photo_uri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            final String email = user_id.getText().toString();
//                            Uri downloadUri = taskSnapshot.getUploadSessionUri();
//                            String downloadURL = downloadUri.toString();
//                            UserInfoData userInfoData = new UserInfoData(email, user_pw.getText().toString(),
//                                    user_name.getText().toString(), downloadURL);
//                            databaseReference.child(email).setValue(userInfoData);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Register.this, "사진이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                                            }else {
                                                Toast.makeText(MyIndeStory.this, "사진이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                //등록 클릭 시 데이터를 shared에 저장한다.
                                alertDialog.dismiss();
                            }
                        });
                    }
                });
                alertDialog.show();

            }
        });


        //리사이클러뷰 뿌려주기
        setUpRecyclerView();

        myStoryAdapter.setOnItemMyStoryClickListener(new MyStoryAdapter.OnItemMyStoryClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Intent intent = new Intent(MyIndeStory.this, MyIndeStory2.class);
                intent.putExtra(POSITION, documentSnapshot.getId());
                startActivity(intent);
            }
        });

        //물방울 버튼 클릭 시
        myStoryAdapter.setOnItemVertClickListener(new MyStoryAdapter.OnItemVertClickListener() {
            @Override
            public void onItemClick(final DocumentSnapshot documentSnapshot, int position) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        String email_key = sharedPreferences.getString("email", null);

                        if(dataSnapshot.child(email_key).exists()) {
                            UserInfoData userInfoData = dataSnapshot.child(email_key).getValue(UserInfoData.class);
                            CollectionReference collectionReference = FirebaseFirestore.getInstance()
                                    .collection("MyStory");
                            String id = documentSnapshot.getId();
                            Log.e("id", "" + id);
//                            String name = documentSnapshot.getString("text_picture");
//                            Log.e("name", "" + name);
                            String name = documentSnapshot.getString("name");


                            //내 아이디가 게시글의 이름과 다르면 사용이 불가하다는 다이얼로그를 띄운다.(수정, 삭제 포함)
                            //물방울 세개 버튼 누르면 게시물 수정, 삭제 가능 창을 띄운다.
                            if(userInfoData.getStr_user_name().equals(name)) {
                                //게시물 수정, 삭제 선택 다이얼로그 실행
                                final AlertDialog.Builder builder = new AlertDialog.Builder(MyIndeStory.this);

                                builder.setItems(R.array.edit, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int pos) {
                                        String[] items = getResources().getStringArray(R.array.edit);
                                        if (items[pos].equals("게시물 수정")) {
                                            //게시물 수정 다이얼로그를 띄운다.
                                            final AlertDialog.Builder alert = new AlertDialog.Builder(MyIndeStory.this);
                                            View v = LayoutInflater.from(MyIndeStory.this).inflate(R.layout.my_inde_story_dialog, null);
                                            alert.setView(v);

                                            floating_user_image = v.findViewById(R.id.mystory_floating_user_image);
                                            floating_user_name = v.findViewById(R.id.mystroy_floating_user_name);
                                            final RelativeLayout layout = v.findViewById(R.id.mystroy_floating_image_layout);
                                            final EditText editText = v.findViewById(R.id.mystory_floating_text);
                                            final Button button = v.findViewById(R.id.mystroy_floating_btn);
                                            imageView = v.findViewById(R.id.mystory_floating_image);

                                            //파베 사용자 데이터를 플로팅 버튼 위에 사용자 정보 표시하기
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                                                    String email_key = sharedPreferences.getString("email", null);
                                                    Log.e("email_key", "" + email_key);

                                                    if(dataSnapshot.child(email_key).exists()) {
                                                        //파베 데이터 불러오기
                                                        UserInfoData userInfoData = dataSnapshot.child(email_key).getValue(UserInfoData.class);
                                                        Log.e("email_key1", "" + email_key);

                                                        Log.e("userInfo", "" + userInfoData);
                                                        Glide.with(MyIndeStory.this).load(userInfoData.getStr_user_image())
                                                                .into(floating_user_image);
                                                        floating_user_name.setText(userInfoData.getStr_user_name());
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            //다이얼로그 띄우기
                                            final AlertDialog builderDialog = alert.create();

                                            //imageView 클릭 시
                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    //카메라, 갤러리 선택 다이얼로그 실행
                                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(MyIndeStory.this);

                                                    builder2.setTitle("사진 업로드");

                                                    builder2.setItems(R.array.camera, new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int pos) {
                                                            String[] items = getResources().getStringArray(R.array.camera);
                                                            if (items[pos].equals("카메라")) {
                                                                //카메라 실행
                                                                takePhoto_edit();

                                                            } else if (items[pos].equals("갤러리")) {
                                                                //갤러리 실행
                                                                Intent iGallery = new Intent(Intent.ACTION_PICK);
                                                                iGallery.setType(MediaStore.Images.Media.CONTENT_TYPE);
                                                                startActivityForResult(iGallery, REQUEST_EDIT_IMAGE_GALLERY);
                                                            }

                                                        }
                                                    });
                                                    //카메라 다이얼로그 실행
                                                    final AlertDialog alertDialog2 = builder2.create();
                                                    alertDialog2.show();


                                                    //등록 버튼 클릭 시
                                                    button.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                                //현재 날짜 가져오는 메서드 실행
                                                                getTime();

                                                            //사진만 수정하거나
                                                            //글만 수정할 수 있게 한다.
                                                                databaseReference.addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        user_text = editText.getText().toString();

                                                                        //사진 수정 시
                                                                            Log.e("photour2", "" + photoUri.toString());


                                                                            if(photoUri != null) {

                                                                                final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                                                                                        +"."+getFileExtension(photoUri));

                                                                                fileReference.putFile(photoUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                                                                    @Override
                                                                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                                                        if (!task.isSuccessful()) {
                                                                                            throw task.getException();
                                                                                        }
                                                                                        return fileReference.getDownloadUrl();
                                                                                    }
                                                                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Uri> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            Uri downloadUri = task.getResult();

                                                                                            myStoryRef.document(documentSnapshot.getId())
                                                                                                    .update("text_picture", downloadUri.toString());

                                                                                            //글 수정 시
                                                                                            if(!user_text.equals("")) {
                                                                                                //사진을 선택하지 않았을때, 글만 변경함
                                                                                                //기존에 있던 사진을 photoUri에 넣고 글과 함께 기존의
                                                                                                //데이터베이스에 넣어준다.
                                                                                                myStoryRef.document(documentSnapshot.getId())
                                                                                                        .update("logined_text", user_text);
                                                                                                Toast.makeText(MyIndeStory.this, "수정 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                            Toast.makeText(MyIndeStory.this, "수정 완료하였습니다.", Toast.LENGTH_SHORT).show();

                                                                                        } else {
                                                                                            Toast.makeText(MyIndeStory.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    }
                                                                                });

//            storageTask = fileReference.putFile(photo_uri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            final String email = user_id.getText().toString();
//                            Uri downloadUri = taskSnapshot.getUploadSessionUri();
//                            String downloadURL = downloadUri.toString();
//                            UserInfoData userInfoData = new UserInfoData(email, user_pw.getText().toString(),
//                                    user_name.getText().toString(), downloadURL);
//                            databaseReference.child(email).setValue(userInfoData);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Register.this, "사진이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                                                                            }else {
                                                                                Toast.makeText(MyIndeStory.this, "사진이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });

                                                                builderDialog.dismiss();
                                                        }
                                                    });

                                                }
                                            });
                                            //수정 다이얼로그 실행
                                            builderDialog.show();

                                        } else if (items[pos].equals("게시물 삭제")) {
                                            myStoryRef.document(documentSnapshot.getId())
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(MyIndeStory.this, "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }

                                    }
                                });
                                //전체 다이얼로그 실행
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                                //두 이름이 다르면
                            } else {

                                final AlertDialog.Builder builder = new AlertDialog.Builder(MyIndeStory.this);
                                builder.setTitle("나의 독립 이야기 수정 불가");
                                builder.setMessage("본인이 아니므로 수정이 불가합니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.show();

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });
            }
        });




        //        //해당 포지션의 user_text 누르면 댓글 볼 수 있는 액티비티로 넘어감
//        myStoryAdapter.setOnItemMyStoryClickListener(new MyStoryAdapter.OnItemMyStoryClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(MyIndeStory.this, MyIndeStory2.class);
//                intent.putExtra(POSITION, position);
//                startActivity(intent);
//            }
//        });
//
        //나중에 내 아이디가 게시글의 이름과 다르면 사용이 불가하다는 다이얼로그를 띄운다.(수정, 삭제 포함)
        //물방울 세개 버튼 누르면 게시물 수정, 삭제 가능 창을 띄운다.
//        myStoryAdapter.setOnItemMoreVertClickListener(new MyStoryAdapter.OnItemMoreVertClickListener() {
//            @Override
//            public void onItemClick(final MyStoryData data) {
//
//                //물방울 버튼을 눌렀을때 해당 포지션의 스토리를 등록한 이름과 로그인된 사용자의 이름을 비교하여
//                //같으면 권한을 부여 다르면 수정, 삭제 불가 다이얼로그를 띄운다.
//                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//                String email_key = sharedPreferences.getString("email", null);
//                String user_info = sharedPreferences.getString(email_key, null);
//                try {
//                    JSONArray jsonArray = new JSONArray(user_info);
//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                    logined_name = jsonObject.getString("name");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//                //물방울 버튼을 눌렀을때 해당 포지션의 스토리를 등록한 이름과 로그인된 사용자의 이름을 비교하여
//                //같으면 권한을 부여 다르면 수정, 삭제 불가 다이얼로그를 띄운다.
//                if(myStoryDataArrayList.get(myStoryAdapter.get_position()).getLogined_name().equals(logined_name)) {
//
//                    //게시물 수정, 삭제 선택 다이얼로그 실행
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(MyIndeStory.this);
//
//                    builder.setItems(R.array.edit, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int pos) {
//                            String[] items = getResources().getStringArray(R.array.edit);
//                            if (items[pos].equals("게시물 수정")) {
//                                //게시물 수정 다이얼로그를 띄운다.
//                                final AlertDialog.Builder alert = new AlertDialog.Builder(MyIndeStory.this);
//                                View v = LayoutInflater.from(MyIndeStory.this).inflate(R.layout.my_inde_story_dialog, null);
//                                alert.setView(v);
//
//                                floating_user_image = v.findViewById(R.id.mystory_floating_user_image);
//                                floating_user_name = v.findViewById(R.id.mystroy_floating_user_name);
//                                final RelativeLayout layout = v.findViewById(R.id.mystroy_floating_image_layout);
//                                final EditText editText = v.findViewById(R.id.mystory_floating_text);
//                                final Button button = v.findViewById(R.id.mystroy_floating_btn);
//                                imageView = v.findViewById(R.id.mystory_floating_image);
//
//                                //사용자 정보 가져와서 상단에 뿌리기(이미지, 이름)
//                                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//                                String email_key = sharedPreferences.getString("email", null);
//                                String user_info = sharedPreferences.getString(email_key, null);
//                                try {
//                                    JSONArray jsonArray = new JSONArray(user_info);
//                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                                    floating_user_image.setImageURI(Uri.parse(jsonObject.getString("imageUri")));
//                                    floating_user_name.setText(jsonObject.getString("name"));
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                                final AlertDialog builderDialog = alert.create();
//
//                                //기존의 데이터를 수정 다이얼로그 위에 뿌려준다.
//                                imageView.setImageURI(data.getText_picture());
//                                editText.setText(data.getLogined_text());
//
//                                //imageView 클릭 시
//                                imageView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//
//                                        //카메라, 갤러리 선택 다이얼로그 실행
//                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(MyIndeStory.this);
//
//                                        builder2.setTitle("사진 업로드");
//
//                                        builder2.setItems(R.array.camera, new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int pos) {
//                                                String[] items = getResources().getStringArray(R.array.camera);
//                                                if (items[pos].equals("카메라")) {
//                                                    //카메라 실행
//                                                    takePhoto_edit();
//
//                                                } else if (items[pos].equals("갤러리")) {
//                                                    //갤러리 실행
//                                                    Intent iGallery = new Intent(Intent.ACTION_PICK);
//                                                    iGallery.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                                                    startActivityForResult(iGallery, REQUEST_EDIT_IMAGE_GALLERY);
//                                                }
//
//                                            }
//                                        });
//                                        //카메라 다이얼로그 실행
//                                        final AlertDialog alertDialog2 = builder2.create();
//                                        alertDialog2.show();
//
//
//                                        //등록 버튼 클릭 시
//                                        button.setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View view) {
//                                                //현재 날짜 가져오는 메서드 실행
//                                                getTime();
//
//                                                //리사이클러뷰에 기존에 있던 데이터 + 입력한 데이터 같이 뿌려주기
//                                                //기존에 있던 데이터를 shared에서 불러와서 넣어주고, 입력한 데이터와 같이 데이터에 넣어준다.
//                                                user_text = editText.getText().toString();
//
//                                                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//                                                String email_key = sharedPreferences.getString("email", null);
//                                                String user_info = sharedPreferences.getString(email_key, null);
//                                                try {
//                                                    JSONArray jsonArray = new JSONArray(user_info);
//                                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//                                                    MyStoryData myStoryData = new MyStoryData(Uri.parse(jsonObject.getString("imageUri")),
//                                                            jsonObject.getString("name"), getTime(), photoUri, jsonObject.getString("name"), user_text);
//                                                    myStoryDataArrayList.set(myStoryAdapter.get_position(), myStoryData);
//
//                                                    //등록 클릭 시 데이터를 shared에 저장한다.
//                                                    save_data();
//
//                                                    myStoryAdapter.notifyDataSetChanged();
//                                                    builderDialog.dismiss();
//
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//
//                                            }
//                                        });
//
//                                    }
//                                });
//                                //수정 다이얼로그 실행
//                                builderDialog.show();
//
//
//                            } else if (items[pos].equals("게시물 삭제")) {
//                                Log.e("게시물 삭제", "" + myStoryAdapter.get_position());
//                                //게시물 삭제 클릭 시
//                                myStoryAdapter.removeItem(myStoryAdapter.get_position());
//                            }
//
//                        }
//                    });
//                    //전체 다이얼로그 실행
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//
//                    //두 이름이 다르면
//                } else {
//
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(MyIndeStory.this);
//                    builder.setTitle("나의 독립 이야기 수정 불가");
//                    builder.setMessage("본인이 아니므로 수정이 불가합니다.");
//                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    builder.show();
//
//                }

//            }
//        });
//
//        myStoryAdapter.notifyDataSetChanged();

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }


    private void setUpRecyclerView() {
        Query query = myStoryRef.orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<MyStoryData> options = new FirestoreRecyclerOptions.Builder<MyStoryData>()
                .setQuery(query, MyStoryData.class)
                .build();

        myStoryAdapter = new MyStoryAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.mystory_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myStoryAdapter);

    }

    protected void onStart() {
        super.onStart();
        myStoryAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myStoryAdapter.stopListening();
    }

//현재 날짜 받기
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    //사진, 갤러리 퍼미션
    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //권한 요청 성공
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
    //사진 찍고 이미지 받아오기
    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            //API24이상이면(NOUGANET)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                photoUri = FileProvider.getUriForFile(this,
                        "com.memory1.independence74.provider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_CAMERA);

            } else {

                photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_CAMERA);

            }
        }
    }

    private void takePhoto_edit() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            //API24이상이면(NOUGANET)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                photoUri = FileProvider.getUriForFile(this,
                        "com.memory1.independence74.provider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_EDIT_CAMERA);

            } else {

                photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_EDIT_CAMERA);

            }
        }
    }

    //이미지 파일 생성
//    private File createImageFile() throws IOException {
//
//        // 이미지 파일 이름 ( blackJin_{시간}_ )
//        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
//        String imageFileName = "blackJin_" + timeStamp + "_";
//
//        // 이미지가 저장될 폴더 이름 ( blackJin )
//        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
//        if (!storageDir.exists()) storageDir.mkdirs();
//
//        // 빈 파일 생성
//        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
//
//        Log.e("image", "" + image.toString());
//        return image;
//    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    //인텐트 실행 결과
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        tempFile = null;
                    }
                }
            }

            return;
        }

        switch (requestCode) {
            //게시물 추가 시 갤러리 실행
            case REQUEST_IMAGE_GALLERY: {
                photoUri = data.getData();

                Cursor cursor = null;

                try {
                    String[] proj = {MediaStore.Images.Media.DATA};

                    assert photoUri != null;
                    cursor = getContentResolver().query(photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();

                    tempFile = new File(cursor.getString(column_index));

                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }


                Glide.with(MyIndeStory.this).load(photoUri).into(floating_image);
                //플로팅 이미지위에 uri 주소값으로 가져온 이미지를 뿌린다.
            }
            //게시물 추가 시 사진 실행
            case REQUEST_CAMERA: {
                //카메라 촬영
                Glide.with(MyIndeStory.this).load(photoUri).into(floating_image);
                break;
            }
            //게시물 수정 시 갤러리 실행
            case REQUEST_EDIT_IMAGE_GALLERY: {
                photoUri = data.getData();

                Cursor cursor = null;

                try {
                    String[] proj = {MediaStore.Images.Media.DATA};

                    assert photoUri != null;
                    cursor = getContentResolver().query(photoUri, proj, null, null, null);

                    assert cursor != null;
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    cursor.moveToFirst();

                    tempFile = new File(cursor.getString(column_index));

                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

                //플로팅 이미지위에 uri 주소값으로 가져온 이미지를 뿌린다.
                Glide.with(MyIndeStory.this).load(photoUri).into(imageView);
                break;
            }
            //게시물 수정 시 사진 실행
            case REQUEST_EDIT_CAMERA: {
                Glide.with(MyIndeStory.this).load(photoUri).into(imageView);
                break;
            }
        }


    }
}
