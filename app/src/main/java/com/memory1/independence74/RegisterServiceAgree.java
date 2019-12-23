package com.memory1.independence74;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterServiceAgree extends AppCompatActivity {

     private TextView service_textview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_agree_service_layout);

        getSupportActionBar().setTitle("서비스 이용약관");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service_textview = findViewById(R.id.service_textview);


    }

    public void gotoActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
