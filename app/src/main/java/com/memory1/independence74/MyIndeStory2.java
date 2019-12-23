package com.memory1.independence74;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.adapter.MyStoryReplyAdapter;
import com.memory1.independence74.data.MyStoryReplyData;
import com.memory1.independence74.data.UserInfoData;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyIndeStory2 extends AppCompatActivity {

    private MyStoryReplyAdapter myStoryReplyAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MyStoryReplyData> myStoryReplyDataArrayList;
    private LinearLayoutManager linearLayoutManager;

    private ImageView mystory_reply_user_image;
    private TextView mystory_reply_user_name;
    private TextView mystory_reply_user_text;
    private ImageView mystory_logined_user_image;
    private EditText mystory_reply;
    private TextView reply_text;
    private String mystory_str_reply;
    private Button mystory_reply_btn;
    private String position;

    private String user_text;
    private int count;
    private boolean isActive;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference myStoryRef = db.collection("MyStory");
    private final FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

    //현재 날짜 변수
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd(kk:mm:ss)");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_inde_story_reply_layout2);

        getSupportActionBar().setTitle("댓글 확인");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //파이어베이스 데이터베이스 인스턴스 선언
        databaseReference = FirebaseDatabase.getInstance().getReference("user_info");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("my_story");

        mystory_reply_user_image = findViewById(R.id.mystory_reply_user_image);
        mystory_reply_user_name = findViewById(R.id.mystroy_reply_user_name);
        mystory_reply_user_text = findViewById(R.id.mystory_reply_user_text);
        mystory_logined_user_image = findViewById(R.id.mystory_logined_user_image);
        mystory_reply = findViewById(R.id.mystory_logined_user_reply);
        mystory_reply_btn = findViewById(R.id.mystory_reply_btn);
        recyclerView = findViewById(R.id.reply_recyclerview);

        //댓글 달기 editText 클릭 시 레이아웃이 안밀리게 하기
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //포지션 값 받아오기
        Intent intent = getIntent();
        position = intent.getStringExtra(MyIndeStory.POSITION);
        Log.e("position", "" + position);

        mystory_reply.requestFocus();


        //파베 MyStory 디비에서 정보 가져오기
        final DocumentReference docRef = db.document("MyStory/" + position);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String logined_text = document.getString("logined_text");
                        mystory_reply_user_text.setText(logined_text);

                        String user_picture = document.getString("user_picture");
                        Picasso.get().load(user_picture).into(mystory_reply_user_image);

                        String logined_name = document.getString("logined_name");
                        mystory_reply_user_name.setText(logined_name);

                    } else {

                    }
                } else {

                }
            }
        });

        //파베 디비 userInfo에서 현재 접속 사용자 정보 가져오기
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                String email_key = sharedPreferences.getString("email", null);
                if(dataSnapshot.child(email_key).exists()) {

                    UserInfoData userInfoData = dataSnapshot.child(email_key).getValue(UserInfoData.class);

                    CollectionReference collectionReference = FirebaseFirestore.getInstance()
                            .collection("MyStory");
                    Picasso.get().load(userInfoData.getStr_user_image()).into(mystory_logined_user_image);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //리사이클러뷰 뿌리기
        setUpRecyclerView();


        //게시 버튼 클릭 시
        mystory_reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mystory_str_reply = mystory_reply.getText().toString();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        String email_key = sharedPreferences.getString("email", null);
                        if(dataSnapshot.child(email_key).exists() && !mystory_str_reply.equals("")) {

                            UserInfoData userInfoData = dataSnapshot.child(email_key).getValue(UserInfoData.class);
                            CollectionReference collectionReference = FirebaseFirestore.getInstance()
                                    .collection("MyStory/" + position + "/reply");
                            collectionReference.add(new MyStoryReplyData(userInfoData.getStr_user_image(),
                                    userInfoData.getStr_user_name(), mystory_str_reply, getTime()));

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //editText에 있는 글 없어지고 키보드 내려가게 하는 로직
                mystory_reply.setText("");
                //키보드 매니저
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mystory_reply_btn.getWindowToken(), 0);
            }
        });
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

//    //데이터 저장
//    private void save_data() {
//        SharedPreferences sharedPreferences = getSharedPreferences("public", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        try {
//            JSONArray jsonArray = new JSONArray();
//            for(int i = 0; i < myStoryReplyDataArrayList.size(); i++) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("user_image", myStoryReplyDataArrayList.get(i).getUser_image());
//                jsonObject.put("user_name", myStoryReplyDataArrayList.get(i).getUser_name());
//                jsonObject.put("user_text", myStoryReplyDataArrayList.get(i).getUser_text());
//                jsonArray.put(jsonObject);
//            }
//
//            //키값을 해당 게시글로 설정한다.
//            SharedPreferences sharedPreferences1 = getSharedPreferences("public", MODE_PRIVATE);
//            String share = sharedPreferences1.getString("public", null);
//            try {
//                JSONArray jsonArray1 = new JSONArray(share);
//                JSONObject jsonObject = jsonArray1.getJSONObject(position);
//                user_text = jsonObject.getString("user_text");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            editor.putString(user_text, jsonArray.toString());
//            editor.apply();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    //데이터 뿌리기
//    private void load_data() {
//        SharedPreferences sharedPreferences = getSharedPreferences("public", MODE_PRIVATE);
//
//        //user_text 값을 키값으로 설정하기위해 해당 데이터 받아오기
//        String share1 = sharedPreferences.getString("public", null);
//        try {
//            JSONArray jsonArray1 = new JSONArray(share1);
//            JSONObject jsonObject = jsonArray1.getJSONObject(position);
//            user_text = jsonObject.getString("user_text");
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //user_text 키값으로 하는 데이터 가져오기
//        String share = sharedPreferences.getString(user_text, "");
//        String share_copy = sharedPreferences.getString("reply_copy", null);
//        try {
//        if(share.equals("[]") && share_copy != null) {
//                JSONArray jsonArray = new JSONArray(share_copy);
//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//                    myStoryReplyDataArrayList.add(new MyStoryReplyData(Uri.parse(jsonObject.getString("user_image")),
//                            jsonObject.getString("user_name"), mystory_str_reply));
//                        Log.e("position1", "" + position);
//                        myStoryReplyAdapter.notifyItemChanged(myStoryReplyDataArrayList.size());
//
//        } else {
//                JSONArray jsonArray = new JSONArray(share);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                        myStoryReplyDataArrayList.add(new MyStoryReplyData(Uri.parse(jsonObject.getString("user_image")),
//                                jsonObject.getString("user_name"), jsonObject.getString("user_text")));
//                        myStoryReplyAdapter.notifyItemInserted(i);
////                    }
//
//                }
//
//        }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("reply_copy", null);
//        editor.apply();
//    }


    private void setUpRecyclerView() {
        //바로 리프레시 해주는 함수
        Log.e("와우", "와우");
        Query firstQuery = rootRef.collection("MyStory").document(position).collection("reply")
                .orderBy("date", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<MyStoryReplyData> options = new FirestoreRecyclerOptions.Builder<MyStoryReplyData>()
                .setQuery(firstQuery, MyStoryReplyData.class)
                .build();
        myStoryReplyAdapter = new MyStoryReplyAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myStoryReplyAdapter);
        myStoryReplyAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        myStoryReplyAdapter.startListening();
    }


    @Override
    protected void onPause() {
        super.onPause();
        myStoryReplyAdapter.stopListening();
    }

}
