package com.memory1.independence74;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterAgree extends AppCompatActivity {

     private TextView service_textview;
     private TextView personal_textview;
     private CheckBox service_checkBox;
     private CheckBox personal_checkBox;
     private CheckBox all_checkBox;
     private Button agree_btn;
     private boolean aBoolean;
     private Boolean isall;
     private Boolean second;
     private Boolean third;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_agree_layout);

        getSupportActionBar().setTitle("회원가입 동의");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service_textview = findViewById(R.id.service_textview);
        personal_textview = findViewById(R.id.personal_textview);
        service_checkBox = findViewById(R.id.service_checkBox);
        personal_checkBox = findViewById(R.id.personal_checkBox);
        all_checkBox = findViewById(R.id.all_checkBox);
        agree_btn = findViewById(R.id.agree_btn);


        service_textview.setMovementMethod(new ScrollingMovementMethod());
        personal_textview.setMovementMethod(new ScrollingMovementMethod());

        //체크 활성화 메서드
        all_checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    service_checkBox.setChecked(true);
                    personal_checkBox.setChecked(true);
                } else {
                    service_checkBox.setChecked(false);
                    personal_checkBox.setChecked(false);
                }
            }
        });

        service_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(RegisterServiceAgree.class);
            }
        });

        agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((service_checkBox.isChecked() && personal_checkBox.isChecked()) || all_checkBox.isChecked()) {
                    gotoActivity(Register.class);
                    service_checkBox.setChecked(false);
                    personal_checkBox.setChecked(false);
                    all_checkBox.setChecked(false);
                } else {
                    Toast.makeText(RegisterAgree.this, "가입 이용약관에 필수 동의해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void gotoActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
