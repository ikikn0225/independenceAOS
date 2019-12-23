package com.memory1.independence74;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.adapter.WianbooAdapter;
import com.memory1.independence74.adapter.WianbooAdapter2;
import com.memory1.independence74.data.WianbooVictimData;
import com.memory1.independence74.data.WianbooVictimData2;

import java.util.ArrayList;
import java.util.List;

public class WianbooMain extends AppCompatActivity {

    List<WianbooVictimData> mWianbooVictimData;
    List<WianbooVictimData2> mWianbooVictimData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wianboo_main_layout);

        getSupportActionBar().setTitle("위안부 피해자");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWianbooVictimData = new ArrayList<>();
        mWianbooVictimData.add(new WianbooVictimData("이옥선 할머니", "1943 ~ 1945", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("강일출 할머니", "1943 ~ 1945", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("박옥선 할머니", "1943 ~ 1945", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("이옥선 할머니", "1943 ~ 1945", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("정복수 할머니", "1943 ~ 1945", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("하수임 할머니", "1943 ~ 1945", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("안점순 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("박필근 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("길원옥 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("이00 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));
        mWianbooVictimData.add(new WianbooVictimData("000 할머니", "정확치 않음", R.drawable.wianboo_girl_main_new));

        recyclerview();

        mWianbooVictimData2 = new ArrayList<>();
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));
        mWianbooVictimData2.add(new WianbooVictimData2("000", "정확치 않음", R.drawable.wianboo_girl_main));






        recyclerview2();

    }

    private void gotoActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void recyclerview() {
        RecyclerView recyclerView = findViewById(R.id.wianboo_recyclerview);
        WianbooAdapter wianbooAdapter = new WianbooAdapter(this, mWianbooVictimData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(wianbooAdapter);
    }

    private void recyclerview2() {
        RecyclerView recyclerView2 = findViewById(R.id.wianboo_recyclerview2);
        WianbooAdapter2 wianbooAdapter2 = new WianbooAdapter2(WianbooMain.this, mWianbooVictimData2);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView2.setAdapter(wianbooAdapter2);
    }
}
