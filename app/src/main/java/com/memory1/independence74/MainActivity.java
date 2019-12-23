package com.memory1.independence74;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.memory1.independence74.data.UserInfoData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView black_screen;
    private ImageView inde_main;
    private ImageView wianboo_main;
    private ImageView map_main;

    private Bitmap bitmap;

    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle toggle;

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;

    private long backPressedTime;
    private Toast backToast;

    public static final String NAME = "com.example.independence74";

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        getSupportActionBar().setTitle("기억");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 파이어베이스 아이디 확인
        mAuth = FirebaseAuth.getInstance();
        //파이어베이스 데이터베이스 인스턴스 선언
        databaseReference = FirebaseDatabase.getInstance().getReference("user_info");


        black_screen = findViewById(R.id.black_screen);
        inde_main = findViewById(R.id.inde_main);
        wianboo_main = findViewById(R.id.wianboo_girl_main);
        map_main = findViewById(R.id.inde_map_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            /* perform your actions here*/


        } else {
            signInAnonymously();
        }


        //이미지 permission
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        } else {
        }


        //네비게이션바
        drawer_layout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawer_layout, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navi_view);
        navigationView.setNavigationItemSelectedListener(this);

        //이메일 값, 네이게이션에 셋
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent intent = getIntent();
        String text = intent.getStringExtra(Login.EMAIL);

//        if(text != null) {
//            //"email"키 값에 text값 저장(저걸로 로그인 유지 상태 만듬)
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("email", text);
//            editor.apply();
//        }

        //이메일을 입력받으면(혹은 게스트가 아니면)
        final String login_state = sharedPreferences.getString("email", null);
        final boolean login_check = sharedPreferences.getBoolean("logine_check", true);

        if(login_state != null) {
                View nav_header_view = navigationView.getHeaderView(0);
                final TextView navi_user_email = nav_header_view.findViewById(R.id.navi_user_email);
                final TextView navi_user_name = nav_header_view.findViewById(R.id.navi_user_name);
                final ImageView navi_user_image = nav_header_view.findViewById(R.id.navi_user_image);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(login_state).exists()) {
                            //파베 데이터 불러오기
                            UserInfoData userInfoData = dataSnapshot.child(login_state).getValue(UserInfoData.class);
                            navi_user_email.setText(userInfoData.getStr_user_id());
                            navi_user_name.setText(userInfoData.getStr_user_name());
//                    navi_user_image.setImageBitmap(StringToBitMap(userInfoData.getStr_user_image()));
//                    Picasso.get().load(userInfoData.getStr_user_image()).into(navi_user_image);
                            Log.e("haha", "haha");
                            Glide.with(MainActivity.this).load(userInfoData.getStr_user_image()).into(navi_user_image);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }
        if(!login_check) {
            editor.putBoolean("logine_check", true);
            editor.apply();
            gotoActivity(Login.class);
        }

        final SharedPreferences sharedPreferences1 = getSharedPreferences("boolean", MODE_PRIVATE);
        boolean no_again = sharedPreferences1.getBoolean("boolean_my_story", true);

        if(no_again) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("나의 독립 이야기 소개");
            builder.setMessage("왼쪽 위에 위치한 버튼을 누르면 ‘나의 독립 이야기’란이 존재합니다. 독립에 대한 나만의 이야기를 공유해주세요.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //false로 바꿔주고 다시 보지 않기 설정완료한다.
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putBoolean("boolean_my_story", false);
                    editor.apply();
                    dialogInterface.dismiss();
                }
            });

            builder.show();
        }


        //독립 운동가 메인으로 이동
        inde_main.setClickable(true);
        inde_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(IndependentMain.class);
            }
        });

        //위안부 강제동원 메인으로 이동
        wianboo_main.setClickable(true);
        wianboo_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(WianbooMain.class);
            }
        });

        //지도 메인으로 이동
        map_main.setClickable(true);
        map_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(Map.class);
            }
        });


    }

    //토글 버튼 클릭 시
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.navi_login:
                //네비게이션바에 내 정보가 있으면
                //이메일 값, 네이게이션에 셋
                Intent intent = getIntent();
                String text = intent.getStringExtra(Login.EMAIL);
                SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                String login_state2 = sharedPreferences.getString("email", null);
                //로그인화면에서 넘어오는 이메일 가입정보가 없으면(게스트 상태이니까 로그인 화면으로 이동)
                if(login_state2 == null) {
                    gotoActivity(Login.class);
                }
                else {
                    //똑같이 로그인 화면으로 이동하지만 로그인 내역을 지워주고나서 로그인 화면으로 이동해야한다.
                    //네이게이션 정보에 이미지, 이름, 이메일 ""값 만들어준 다음 로그인 화면으로 이동

                    //로그아웃 확인 다이얼로그
                    final AlertDialog.Builder logout_dialog = new AlertDialog.Builder(MainActivity.this);
                    logout_dialog.setTitle("로그아웃");
                    logout_dialog.setMessage("로그아웃 하시겠습니까?");
                    logout_dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editor.putString("email", null);
                            editor.apply();

                            //파이어베이스 로그아웃 실행
                            mAuth.signOut();

                            gotoActivity(Login.class);
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
                break;
            case R.id.navi_register:
                gotoActivity(RegisterAgree.class);
                break;
            case R.id.navi_inde:
                gotoActivity(IndependentMain.class);
                break;
            case R.id.navi_wianboo:
                gotoActivity(WianbooMain.class);
                break;
            case R.id.navi_map:
                gotoActivity(Map.class);
                break;
            case R.id.navi_my_story:
                SharedPreferences sharedPreferences2 = getSharedPreferences("userInfo", MODE_PRIVATE);
                final String login_state4 = sharedPreferences2.getString("email", null);

                if (login_state4 == null) {
                    //게스트이므로 수정 할 수 없다는 다이얼로그 띄운다.
                    final AlertDialog.Builder logout_dialog_my_story = new AlertDialog.Builder(MainActivity.this);
                    logout_dialog_my_story.setTitle("나의 독립 이야기 접근 불가");
                    logout_dialog_my_story.setMessage("로그인이 필요한 서비스입니다.");
                    logout_dialog_my_story.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    logout_dialog_my_story.setNegativeButton("회원가입", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gotoActivity(Register.class);
                        }
                    });
                    logout_dialog_my_story.setNeutralButton("로그인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                             gotoActivity(Login.class);
                        }
                    });
                    logout_dialog_my_story.show();

                } else {
                    gotoActivity(MyIndeStory.class);
                }
                break;
            case R.id.navi_user_setting:
                SharedPreferences sharedPreferences1 = getSharedPreferences("userInfo", MODE_PRIVATE);
                final String login_state3 = sharedPreferences1.getString("email", null);

                if (login_state3 == null) {
                    //게스트이므로 수정 할 수 없다는 다이얼로그 띄운다.
                    final AlertDialog.Builder logout_dialog = new AlertDialog.Builder(MainActivity.this);
                    logout_dialog.setTitle("사용자 설정 접근 불가");
                    logout_dialog.setMessage("회원이 아니므로 사용자 설정에 접근이 불가합니다.");
                    logout_dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    logout_dialog.show();

                } else {
                    gotoActivity(UserSetting.class);
                }
                break;
        }

        return true;
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                /* perform your actions here*/

            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("MainActivity", "signFailed****** ", exception);
                    }
                });
    }

    private Bitmap getBitmap(String url) {
        URL imgUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        Bitmap retBitmap = null;
        try{
            imgUrl = new URL(url);
            connection = (HttpURLConnection) imgUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            is = connection.getInputStream();
            retBitmap = BitmapFactory.decodeStream(is);
        } catch(Exception e) {
            e.printStackTrace();
            return null; }
        finally {
            if(connection!=null) {
                connection.disconnect(); }
            return retBitmap; }
    }


    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(this, "뒤로 버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
