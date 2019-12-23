package com.memory1.independence74;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.adapter.IndeAdapter2;
import com.memory1.independence74.data.IndeData2;

import java.util.ArrayList;

public class IndependentMain2 extends AppCompatActivity {

    ArrayList<IndeData2> indeDataList;
    TextView inde_fight;
    TextView inde_nofight;
    IndeAdapter2 indeAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inde_main_layout2);

        getSupportActionBar().setTitle("독립 운동가");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inde_fight = findViewById(R.id.inde_fight);
        inde_nofight = findViewById(R.id.inde_nofight);

//        inde_fight.setClickable(true);
        inde_fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(IndependentMain.class);
            }
        });
//        inde_nofight.setClickable(true);
        inde_nofight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(IndependentMain2.class);
            }
        });

        indeDataList = new ArrayList<>();
        indeDataList.add(new IndeData2("유관순", "일제강점기 아우내 3·1만세운동을 주도한 독립운동가.", "유관순 열사의 이력", R.drawable.ryukwansoon, R.drawable.ryukwansoon2, R.drawable.ryukwansoon3, "https://terms.naver.com/entry.nhn?docId=539103&cid=46623&categoryId=46623"));
        indeDataList.add(new IndeData2("남자현", "일제강점기 만주에서 군사기관과 농어촌을 순회하며 독립정신을 고취시킨 독립운동가.", "남자현 열사의 이력", R.drawable.namjahyun, "https://terms.naver.com/entry.nhn?docId=533029&cid=46623&categoryId=46623"));
        indeDataList.add(new IndeData2("손병희", "민족대표 33인 중 비폭력투쟁 호소", "손병희 독립운동가의 이력", R.drawable.sonbyungheui, "https://terms.naver.com/entry.nhn?docId=5740661&cid=59011&categoryId=59011"));
        indeDataList.add(new IndeData2("김원벽", "3·1 독립만세운동을 성공적으로 이끈 학생지도자", "김원벽 독립운동가의 이력", R.drawable.kimwonbyuk, "https://terms.naver.com/entry.nhn?docId=5138594&cid=59011&categoryId=59011"));
        indeDataList.add(new IndeData2("장덕준", "동포의 학살 참극을 알리려다 일제에 암살당한 순직 기자", "장덕준 독립운동가의 이력", R.drawable.jangdeokjoon2, "https://terms.naver.com/entry.nhn?docId=3587875&cid=59011&categoryId=59011"));
        indeDataList.add(new IndeData2("권병덕", "조선이 독립국임과 조선인이 자주민임을 세계에 알린 민족대표", "권병덕 독립운동가의 이력", R.drawable.kwonbyungdeok2, "https://terms.naver.com/entry.nhn?docId=3581181&cid=59011&categoryId=59011"));

        recyclerview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inde_search, menu);

        MenuItem searchItem = menu.findItem(R.id.searchView_inde);
        androidx.appcompat.widget.SearchView searchView =(androidx.appcompat.widget.SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                indeAdapter2.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    private void gotoActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void recyclerview() {
        RecyclerView recyclerView = findViewById(R.id.inde_recyclerview2);
        indeAdapter2 = new IndeAdapter2(this, indeDataList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(indeAdapter2);
    }
}