package com.memory1.independence74;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IndeDetail extends AppCompatActivity {

    TextView inde_name, inde_detail_history, inde_detail_work;
    ImageView inde_detail_image, inde_detail_image2, inde_detail_image3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inde_detail);

        inde_name = findViewById(R.id.inde_name);
        inde_detail_work = findViewById(R.id.inde_detail_work);
        inde_detail_image = findViewById(R.id.inde_detail_image);
        inde_detail_image2 = findViewById(R.id.inde_detail_image2);
        inde_detail_image3 = findViewById(R.id.inde_detail_image3);
        inde_detail_history = findViewById(R.id.inde_detail_history);


        //카드뷰에서 전송한 데이터 받기
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String work = intent.getExtras().getString("work");
        int image = intent.getExtras().getInt("image");
        int image2= intent.getExtras().getInt("image2");
        int image3 = intent.getExtras().getInt("image3");
        String history = intent.getExtras().getString("history");


        //데이터 나타내기
        inde_name.setText(name);
        inde_detail_work.setText(work);
        inde_detail_image.setImageResource(image);
        inde_detail_image2.setImageResource(image2);
        inde_detail_image3.setImageResource(image3);
        inde_detail_history.setText(history);


        //툴바에 해당 독립운동가 이름 표시
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
