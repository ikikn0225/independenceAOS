package com.memory1.independence74;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.memory1.independence74.adapter.IndeAdapter;
import com.memory1.independence74.data.IndeData;

import java.util.ArrayList;

public class IndependentMain extends AppCompatActivity {

    ArrayList<IndeData> indeDataList1;
    ArrayList<IndeData> filterList = new ArrayList<>();
    TextView inde_fight;
    TextView inde_nofight;
    private ImageView searchButton;
    private EditText searchView_editText;
    IndeAdapter indeAdapter;

    //다이얼로그 다시보지않기 불리언
    private int no_again = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inde_main_layout);

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

        //아래는 독립 투쟁을 양분화한 이유 및 유의 사항 다이얼로그 띄우기
        //게스트 상태로 들어올때 독립 투쟁을 양분화 할 수 없다는 글을 띄운다.
        //큰 틀에서 활동을 기반으로 구분한 것이다.
        //다시 보지 않기 버튼을 누르면 다시 뜨지 않는다.
        final SharedPreferences sharedPreferences = getSharedPreferences("boolean", MODE_PRIVATE);
        boolean no_again = sharedPreferences.getBoolean("boolean", true);

        if(no_again) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(IndependentMain.this);
            builder.setTitle("유의 사항");
            builder.setMessage("독립 투쟁의 영향을 양분화 할 수 없지만, 구분하기 쉽도록 업적을 기준으로 큰 틀에서 나누었음을 유의바랍니다.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //그냥 확인
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("다시 보지 않기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //false로 바꿔주고 다시 보지 않기 설정완료한다.
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("boolean", false);
                    editor.apply();

                    dialogInterface.dismiss();
                }
            });

            builder.show();
        }



        indeDataList1 = new ArrayList<>();
        indeDataList1.add(new IndeData("김구", "일제강점기 안명근사건, 안중근 하얼빈 의거, 모스크바3상회의 등과 관련된 독립운동가. 정치인.","4세 때 심한 천연두를 앓아 가까스로 목숨을 건졌고, 9세에 한글과 한문을 배우기 시작하였으며, 아버지의 열성으로 집안에 서당을 세웠다.\n" +
                "\n" +
                "14세에 『통감』·『사략』과 병서를 즐겨 읽었으며, 15세에는 정문재(鄭文哉)의 서당에서 본격적인 한학수업에 정진하였고, 17세에 조선왕조 최후의 과거에 응시하였으나 뜻을 이루지 못하였다.\n" +
                "\n" +
                "벼슬자리를 사고 파는 부패된 세태에 울분을 참지 못하여 18세에 동학에 입도하였으며, 황해도 도유사(都有司)의 한 사람으로 뽑혀 제2대 교주 최시형(崔時亨)을 만났다.\n" +
                "\n" +
                "19세에 팔봉접주(八峰接主)가 되어 동학군의 선봉장으로 해주성(海州城)을 공략하였는데, 이 사건으로 1895년 신천안태훈(安泰勳)의 집에 은거하며, 당시 그의 아들 중근(重根)과도 함께 지냈다.\n" +
                "\n" +
                "또한, 해서지방의 선비 고능선(高能善) 문하에서 훈도를 받았고, 항일의식을 참지 못하여 압록강을 건너 남만주 김이언(金利彦)의 의병부대에 몸담았다.\n" +
                "\n" +
                "을미사변으로 충격을 받고 귀향을 결심, 1896년 2월 안악 치하포(鴟河浦)에서 왜병 중위 쓰치다[土田讓亮]를 맨손으로 처단하여 21세의 의혈청년으로 국모의 원한을 푸는 첫 거사를 결행하였다.\n" +
                "\n" +
                "그 해 5월 집에서 은신중 체포되어 해주감옥에 수감되었고, 7월 인천 감리영(監理營)에 이감되었으며, 다음해인 1897년 사형이 확정되었다. 사형집행 직전 고종황제의 특사로 집행이 중지되었으나, 석방이 되지 않아 이듬해 봄에 탈옥하였다.\n" +
                "\n" +
                "삼남일대를 떠돌다가 공주 마곡사에 입산하여 승려가 되어 원종(圓宗)이란 법명을 받았고, 1899년 서울 새절(봉원사)을 거쳐 평양 근교 대보산(大寶山)영천암(靈泉庵)의 주지가 되었다가 몇 달 만에 환속하였다.\n" +
                "\n" +
                "수사망을 피해 다니면서도 황해도 장연에서 봉양학교(鳳陽學校) 설립을 비롯하여, 교단 일선에서 계몽·교화사업을 전개하였으며, 20대 후반에 기독교에 입교하여 진남포예수교회 에버트청년회(Evert靑年會) 총무로 일했다.\n" +
                "\n" +
                "이런 가운데 1905년 을사조약이 체결되자 상경하여 상동교회 지사들의 조약반대 전국대회에 참석하였으며, 이동녕(李東寧)·이준(李儁)·전덕기(全德基) 등과 을사조약의 철회를 주장하는 상소를 결의하고 대한문 앞에서 읍소하면서 종로에서 가두연설에 나서기도 하였다.\n" +
                "\n" +
                "한편, 종로에서 가두연설에 나서기도 하여 구국대열에 앞장섰다. 1906년 해서교육회(海西敎育會) 총감으로 학교설립을 추진하여, 다음해 안악에 양산학교(楊山學校)를 세웠다.\n" +
                "\n" +
                "1909년 전국 강습소 순회에 나서서 애국심을 고취하는 한편, 재령 보강학교(保强學校) 교장이 되었다. 그때 비밀단체 신민회(新民會)의 회원으로 구국운동에도 가담하였다. 그 해 가을 안중근의 거사에 연루되어 해주감옥에 투옥되었다가 석방되었다.\n" +
                "\n" +
                "그 뒤 1911년 1월 데라우치[寺內正毅] 총독의 암살을 모의했다는 혐의로 안명근(安明根)사건의 관련자로 체포되어 17년형을 선고받았다.\n" +
                "\n" +
                "1914년 7월 감형으로 형기 2년을 남기고 인천으로 이감되었다가 가출옥여 김홍량(金鴻亮)의 동산평(東山坪) 농장관리인으로 농촌부흥운동에 주력하였다.\n" +
                "\n" +
                "1919년 3·1운동 직후에 상해로 망명하여 대한민국 임시정부의 초대 경무국장이 되었고, 1923년 내무총장, 1924년 국무총리 대리, 1926년 12월 국무령(國務領)에 취임하였다.\n" +
                "\n" +
                "이듬해 헌법을 제정, 임시정부를 위원제로 고치면서 국무위원이 되었다. 1929년 재중국 거류민단 단장을 역임하였고 1930년 이동녕·이시영(李始榮) 등과 한국독립당(韓國獨立黨)을 창당하였다.\n" +
                "\n" +
                "1931년 한인애국단을 조직, 의혈청년들로 하여금 직접 왜적 수뇌의 도륙항전(屠戮抗戰)에 투신하도록 지도력을 발휘하였다.\n" +
                "\n" +
                "이에 중국군 김홍일(金弘壹) 및 상해병공창 송식표(宋式驫)의 무기공급과 은밀한 거사준비에 따라, 1932년 1·8이봉창(李奉昌)의거와 4·29윤봉길(尹奉吉)의거를 주도한 바 있는데, 윤봉길의 이 의거가 성공하여 크게 이름을 떨쳤다.\n" +
                "\n" +
                "1933년 장개석(蔣介石)을 만나 한·중 양국의 우의를 돈독히 하고 중국 뤄양군관학교[洛陽軍官學校]를 광복군 무관양성소로 사용하도록 합의를 본 것은 주목받을 성과였으며, 독립운동가들에게 큰 용기를 주었다. 1934년 임시정부 국무령에 재임되었고, 1940년 3월 임시정부 국무위원회 주석에 취임하였다.\n" +
                "\n" +
                "같은해 충칭[重慶]에서 한국광복군을 조직하고 총사령관에 지청천(池靑天), 참모장에 이범석(李範奭)을 임명하여 항일무장부대를 편성하고, 일본의 진주만 기습에 즈음하여 1941년 12월 대한민국 임시정부의 이름으로 대일선전포고를 하면서 임전태세에 돌입하였다.\n" +
                "\n" +
                "1942년 7월 임시정부와 중국정부 간에 광복군 지원에 대한 정식협정이 체결되어, 광복군은 중국 각 처에서 연합군과 항일공동작전에 나설 수 있었다.\n" +
                "\n" +
                "그 뒤 개정된 헌법에 따라 1944년 4월 충칭 임시정부 주석으로 재선되고, 부주석에 김규식(金奎植), 국무위원에 이시영·박찬익 등이 함께 취임하였다.\n" +
                "\n" +
                "그리고 일본군에 강제 징집된 학도병들을 광복군에 편입시키는 한편, 산시성[陜西省]시안[西安]과 안후이성[安徽省] 푸양[阜陽]에 한국광복군 특별훈련반을 설치하면서 미육군전략처와 제휴하여 비밀특수공작훈련을 실시하는 등, 중국 본토와 한반도 수복의 군사훈련을 적극 추진하고 지휘하던 중 시안에서 8·15광복을 맞이하였다.\n" +
                "\n" +
                "1945년 11월 임시정부 국무위원과 함께 제1진으로 환국하였다. 그 해 12월 28일 모스크바 3상회의에서의 신탁통치결의가 있자 신탁통치반대운동에 적극 앞장섰으며, 오직 자주독립의 통일정부 수립을 목표로 정계를 영도해 나갔다.\n" +
                "\n" +
                "1946년 2월 비상국민회의의 부총재에 취임하였고, 1947년 비상국민회의가 국민회의로 개편되자 부주석이 되었다. 그 해 6월 30일 일본에서 운구해온 윤봉길·이봉창(李奉昌)·백정기(白貞基) 등 세 의사의 유골을 첫 국민장으로 효창공원에 봉안하였다.\n" +
                "\n" +
                "이를 전후하여 대한독립촉성중앙협의회와 민주의원(民主議院)·민족통일총본부를 이승만(李承晩)·김규식과 함께 이끌었다. 1947년 11월 국제연합 감시하에 남북총선거에 의한 정부수립결의안을 지지하면서, 그의 논설 「나의 소원」에서 밝히기를 “완전자주독립노선만이 통일정부 수립을 가능하게 한다.”고 역설하였다.\n" +
                "\n" +
                "그러나 1948년 초 북한이 국제연합의 남북한총선거감시위원단인 국제연합한국임시위원단의 입북을 거절함으로써, 선거가능지역인 남한만의 단독선거가 결정되었다. 그러나 이러한 상황에서도 김구는 남한만의 선거에 의한 단독정부수립방침에 절대 반대하는 입장을 취하였다.\n" +
                "\n" +
                "그 해 2월 10일 「3천만동포에게 읍고(泣告)함」이라는 성명서를 통하여 마음속의 38선을 무너뜨리고 자주독립의 통일정부를 세우자고 강력히 호소하였다.\n" +
                "\n" +
                "분단된 상태의 건국보다는 통일을 우선시하여 5·10제헌국회의원선거를 거부하기로 방침을 굳히고, 그 해 4월 19일 남북협상차 평양으로 향하였다.\n" +
                "\n" +
                "김구·김규식·김일성·김두봉(金枓奉) 등이 남북협상 4자회담에 임하였으나, 민족통일정부 수립에 실패하고 그 해 5월 5일 서울로 돌아왔다. 그 뒤 한국독립당의 정비와 건국실천원양성소의 일에 주력하며 구국통일의 역군 양성에 힘썼다.\n" +
                "\n" +
                "남북한의 단독정부가 그 해 8월 15일과 9월 9일에 서울과 평양에 각각 세워진 뒤에도 민족분단의 비애를 딛고 민족통일운동을 재야에서 전개하던 가운데, 이듬해 6월 26일 서울 서대문구의 경교장(京橋莊)에서 육군 소위 안두희(安斗熙)에게 암살당하였다.\n" +
                "\n" +
                "[네이버 지식백과] 김구 [金九] (한국민족문화대백과, 한국학중앙연구원)\n" +
                "\n", R.drawable.kimgoo, R.drawable.kimgoo2, R.drawable.kimgoo3, "https://terms.naver.com/entry.nhn?docId=551770&cid=46626&categoryId=46626"));

        indeDataList1.add(new IndeData("안중근", "일제강점기 이토저격사건과 관련된 독립운동가. 의병장, 의사(義士).", "안중근 열사의 이력", R.drawable.anjoongeun3, "https://terms.naver.com/entry.nhn?docId=579989&cid=46623&categoryId=46623"));
        indeDataList1.add(new IndeData("김좌진", "1889(고종 26)∼1930. 독립운동가.", "김좌진 열사의 이력", R.drawable.kimjwajin, "https://terms.naver.com/entry.nhn?docId=553467&cid=46623&categoryId=46623"));
        indeDataList1.add(new IndeData("홍범도", "일제강점기 대한독립군 총사령관, 대한독립군단 부총재 등을 역임한 독립운동가.", "홍범도 열사의 이력", R.drawable.hongbeomdo, "https://terms.naver.com/entry.nhn?docId=528385&cid=46623&categoryId=46623"));
        indeDataList1.add(new IndeData("윤봉길", "일제강점기 훙커우공원 투탄의거와 관련된 독립운동가. 의사(義士).", "윤봉길 열사의 이력", R.drawable.yunbonggil, "https://terms.naver.com/entry.nhn?docId=540113&cid=46623&categoryId=46623"));
        indeDataList1.add(new IndeData("한규설", "1848(헌종 14)∼1930. 조선 말기의 무신·애국지사.", "한규설 열사의 이력", R.drawable.hangyuseol, "https://terms.naver.com/entry.nhn?docId=526105&cid=46623&categoryId=46623"));
        indeDataList1.add(new IndeData("이규채", "일제강점기 임시정부 의정원 의원, 한국독립당 정치부위원, 신한독립당감찰위원장 등을 역임한 독립운동가.\n" +
                "\n", "이규채 열사의 이력", R.drawable.leekyuchae, "https://terms.naver.com/entry.nhn?docId=541442&cid=46623&categoryId=46623"));
        indeDataList1.add(new IndeData("장준하", "일제강점기 한국광복군 제2지대에 배속되어 활동한 독립운동가. 언론인, 정치인, 민주화운동가.", "장준하 독립운동가의 이력", R.drawable.jangjoonha, "https://terms.naver.com/entry.nhn?docId=538569&cid=46626&categoryId=46626"));
        indeDataList1.add(new IndeData("지청천", "27년간 줄기차게 독립전쟁에 매진한 참 군인, 대한민국임시정부 군무부장, 한국광복군 총사령관", "지청천 독립운동가의 이력", R.drawable.jicheongjcheon, "https://terms.naver.com/entry.nhn?docId=5827178&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("이동휘", "러시아 한인 독립운동의 지도자, 대통령장 1995", "이동휘 독립운동가의 이력", R.drawable.leedongheoi, "https://terms.naver.com/entry.nhn?docId=5778487&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("한용운", "민족대표 33인 불교계 대표", "한용운 독립운동가의 이력", R.drawable.hanyounggeun, "https://terms.naver.com/entry.nhn?docId=5770909&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("최재형", "항일의병투쟁의 지도자", "최재형 독립운동가의 이력", R.drawable.choijaehyung, "https://terms.naver.com/entry.nhn?docId=5753501&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("김마리아", "여성 항일투쟁의 선봉장", "김마리아 독립운동가의 이력", R.drawable.kimmaria, "https://terms.naver.com/entry.nhn?docId=5733629&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("유상근", "광복을 눈앞에 두고 옥중 순국한 한인애국단원", "유상근 독립운동가의 이력", R.drawable.usanggeun, "https://terms.naver.com/entry.nhn?docId=5685636&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("조경환", "광주어등산(魚登山)에서 순국한 불굴의 의병장", "조경환 독립운동가의 이력", R.drawable.jokyunghwan, "https://terms.naver.com/entry.nhn?docId=5681277&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("현천묵", "북간도로 망명하여 무장독립투쟁의 초석을 다진 독립운동가", "현천묵 독립운동가의 이력", R.drawable.hyuncheonmook, "https://terms.naver.com/entry.nhn?docId=5678860&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("최용덕", "무장독립투쟁을 견지한 군인, 대한민국 공군 창설의 주역", "최용덕 독립운동가의 이력", R.drawable.choiyoungdeok, "https://terms.naver.com/entry.nhn?docId=5668489&cid=59011&categoryId=59011"));
        indeDataList1.add(new IndeData("이회영", "위대한 사상가이자 위대한 독립운동가", "이회영 독립운동가의 이력", R.drawable.leeheoyoung, "https://terms.naver.com/entry.nhn?docId=3572354&cid=59011&categoryId=59011"));



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
                indeAdapter.getFilter().filter(s);
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
        RecyclerView recyclerView = findViewById(R.id.inde_recyclerview);
        indeAdapter = new IndeAdapter(this, indeDataList1);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(indeAdapter);
    }
}
