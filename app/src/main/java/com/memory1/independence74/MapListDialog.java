package com.memory1.independence74;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.adapter.MapAdapter;
import com.memory1.independence74.data.MapIndeData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapListDialog extends AppCompatActivity {

    private ArrayList<MapIndeData> mapIndeDataArrayList;
    private RecyclerView recyclerView;
    private MapAdapter mapAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static final String HOMEPAGE = "com.example.independence74";
    public static final String NAME = "com.example.independence74";


    private Button map_inde_btn;
    private Button map_wianboo_btn;

    private boolean doubleClick = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_custom_dialog);

        getSupportActionBar().setTitle("독립 지도 항목");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ArrayList를 독립 운동가, 위안부 피해자 두개를 만들어 정보를 넣어준다.
        map_inde_btn = findViewById(R.id.map_inde_btn);
        map_wianboo_btn = findViewById(R.id.map_wianboo_btn);
        if(mapIndeDataArrayList == null) {
            mapIndeDataArrayList = new ArrayList<>();
        }

//        map_inde_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
                //boolean으로 중복 클릭 및 생성 불가 확인
                if(doubleClick) {
                    SharedPreferences sharedPreferences = getSharedPreferences("distance", MODE_PRIVATE);
                    String distanceTo = sharedPreferences.getString("distance", null);
                    if(distanceTo != null) {
                        try {
                            JSONArray jsonArray = new JSONArray(distanceTo);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if(doubleClick) {
                                    //독립 운동가 ArrayList
                                    mapIndeDataArrayList.add(new MapIndeData("서대문 형무소 역사관", R.drawable.seodaemoon, jsonObject.getString("서대문 형무소 역사관"), "http://www.sscmc.or.kr/newhistory/index_culture.asp", "서울 서대문구 통일로 251"));
                                    mapIndeDataArrayList.add(new MapIndeData("독립 기념관", R.drawable.doklip, jsonObject.getString("독립 기념관"), "http://www.i815.or.kr/", "충남 천안시 동남구 목천읍 독립기념관로 1"));
                                    mapIndeDataArrayList.add(new MapIndeData("안성 3.1운동 기념관", R.drawable.anseong31, jsonObject.getString("안성 3.1운동 기념관"), "경기도 안성시 원곡면 만세로 868"));
                                    mapIndeDataArrayList.add(new MapIndeData("윤봉길의사 기념관", R.drawable.yunbonggil, jsonObject.getString("매헌 윤봉길의사 기념관"), "http://www.yunbonggil.co.kr", "서울 서초구 매헌로 99"));
                                    mapIndeDataArrayList.add(new MapIndeData("조만식 기념관", R.drawable.jomansikginyum, jsonObject.getString("숭실대학교 조만식 기념관"), "http://www.ssu.ac.kr", "서울 동작구 상도동 511"));
                                    mapIndeDataArrayList.add(new MapIndeData("도산 안창호 기념관", R.drawable.anchanghoginyum, jsonObject.getString("도산 안창호 기념관"), "http://www.ahnchangho.or.kr", "서울 강남구 도산대로 45길 20"));
                                    mapIndeDataArrayList.add(new MapIndeData("백범김구 기념관", R.drawable.kimgooginyum, jsonObject.getString("효창공원 백범김구 기념관"), "http://www.kimkoomuseum.org/main", "서울 용산구 임정로 26"));
                                    mapIndeDataArrayList.add(new MapIndeData("안중근 의사 기념관", R.drawable.anjoonggeunginyum, jsonObject.getString("안중근 의사 기념관"), "http://ahnjunggeun.or.kr", "서울 중구 소월로 91"));
                                    mapIndeDataArrayList.add(new MapIndeData("유관순 기념관", R.drawable.ugyunsoonginyum, jsonObject.getString("유관순 기념관"), "http://www.ewha.hs.kr", "서울 중구 통일로 4길 30-1"));
                                    mapIndeDataArrayList.add(new MapIndeData("윤봉길의사 기념관", R.drawable.yunbongilginyum2, jsonObject.getString("윤봉길의사 기념관"), "http://www.yesan.go.kr/ystfo/sub03_01.do", "충청남도 예산군 덕산면 덕산온천로 183-5"));

                                    mapIndeDataArrayList.add(new MapIndeData("이봉창 선생 생가", R.drawable.leebongchanghome, jsonObject.getString("이봉창 선생 생가"), "서울 용산구 백범로 285-5"));
                                    mapIndeDataArrayList.add(new MapIndeData("지청천장군 생가터", R.drawable.jicheongcheonhome, jsonObject.getString("지청천장군 생가터"), "서울 종로구 삼청동 38-1"));
                                    mapIndeDataArrayList.add(new MapIndeData("유관순열사 생가터", R.drawable.ugyunsoonginyumhome, jsonObject.getString("유관순열사 생가터"), "충남 천안시 동남구 병천면 용두리 338-1"));
                                    mapIndeDataArrayList.add(new MapIndeData("박열의사 생가", R.drawable.parkyuolhome, jsonObject.getString("박열의사 생가"), "경북 문경시 마성면 오천리 98"));
                                    mapIndeDataArrayList.add(new MapIndeData("신채호선생 생가", R.drawable.shinchaehohome, jsonObject.getString("신채호선생 생가"), "대전 중구 단재로 228번지 47"));
                                    mapIndeDataArrayList.add(new MapIndeData("채기중선생 생가", R.drawable.chaegijoonghome, jsonObject.getString("채기중선생 생가"), "경북 상주시 이안면 소암리 290-1"));
                                    mapIndeDataArrayList.add(new MapIndeData("윤봉길의사 생가", R.drawable.yunbongilginyumhome, jsonObject.getString("윤봉길의사 생가"), "충남 예산군 덕산면 시량리 135"));

                                    //중복 체크 boolean값 false 변환
                                    doubleClick = false;
                                }
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                    //리사이클러뷰 뿌리기
                    recyclerView();
                    //해당 리사이클러뷰 포지션 클릭 시
                    mapAdapter.setOnItemClickListener(new MapAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            //구글 지도로 이동
//                            int position1 = mapAdapter.get_position();
                            Intent intent = new Intent(MapListDialog.this, Map.class);
                            intent.putExtra(NAME, mapIndeDataArrayList.get(position).getName());
                            intent.putExtra("homepage", mapIndeDataArrayList.get(position).getHomepage_url());
                            startActivity(intent);
                        }
                    });


                }
//            }
//        });

//        map_wianboo_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mapIndeDataArrayList.clear();
//                //독립 운동가 ArrayList
//                mapIndeDataArrayList.add(new MapIndeData("안중근 역사관", "서울시 관악구 우리집", "100km", R.drawable.kimgoo, "https://terms.naver.com/entry.nhn?docId=551770&cid=46626&categoryId=46626"));
//                mapIndeDataArrayList.add(new MapIndeData("안중근 역사관", "서울시 관악구 우리집", "100km", R.drawable.kimgoo, "https://terms.naver.com/entry.nhn?docId=551770&cid=46626&categoryId=46626"));
//                //리사이클러뷰 뿌리기
//                recyclerView();
//
//            }
//        });




    }

    public void recyclerView() {
        recyclerView = findViewById(R.id.map_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mapAdapter = new MapAdapter(mapIndeDataArrayList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mapAdapter);

    }

}
