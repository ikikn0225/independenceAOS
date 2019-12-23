package com.memory1.independence74;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.memory1.independence74.data.MapLocationData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Map extends AppCompatActivity
        implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {


    private GoogleMap mMap;
    private Marker currentMarker = null;

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초


    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;


    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


    Location mCurrentLocatiion;
    LatLng currentPosition;


    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;

    //GoogleMap 객체
    GoogleMap googleMap;


    private Button map_list_open;
    private PopupWindow mPopupWindow ;

    double copy_lat_location;
    double copy_lon_location;

    private boolean locationChecker = false;

    ArrayList<MapLocationData> mapLocationData = new ArrayList();
    Marker selectedMarker;

    private View mLayout;  // Snackbar 사용하기 위해서는 View가 필요합니다.
    // (참고로 Toast에서는 Context가 필요했습니다.)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("독립 지도");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
        setContentView(R.layout.map_layout);
//
        mLayout = findViewById(R.id.layout_main);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //구글맵 연결하고 호출하는 구문
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        //리스트 열고 닫기
        map_list_open = findViewById(R.id.map_list_open);
        map_list_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Map.this, MapListDialog.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d(TAG, "onMapReady :");

        mMap = googleMap;

        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에

        //마커 추가
        getIndeMarkerItems();

        //지도의 초기위치를 서울로 이동
        setDefaultLocation();


        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            startLocationUpdates(); // 3. 위치 업데이트 시작


        }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                        ActivityCompat.requestPermissions( Map.this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);
                    }
                }).show();


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }



        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //마커가 아닌 지도가 클릭되었을 시 선택되어 있던 마커는 선택되지 않은 것처럼 표현
        mMap.setOnMapClickListener(this);

        //현재 위치와 특정 마커 사이의 거리 측정
        getDistance();

        //레이아웃 해당 포지션 클릭 했을때 넘어온 값을 HOMEPAGE에 저장한 것을 가지고 있다가 마커 클릭 시 불러온다.
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("homepage");
                Log.e("name", "" + name);

                if(name != null) {
                    //IndeData에서 URL값을 받아와서 인터넷을 실행한다.
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(name));
                    startActivity(intent1);
                } else  {
                    Toast.makeText(Map.this, "홈페이지가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    //현재 위치와 특정 마커의 거리 측정
    public double getDistance() {
        double distance = 0;
        //현재 위치의 경도와 위도를 담은 객체를 생성
        //특정 마커의 경도와 위도를 담은 객체형태를 데이터를 비교
        //distanceTo 메서드로 비교

        double[] distanceArray = new double [mapLocationData.size()];
        double copy_lat_location2 = 0;
        double copy_lon_location2 = 0;

        //현재 위치
        SharedPreferences sharedPreferences1 = getSharedPreferences("distance", MODE_PRIVATE);
        String distanceTo = sharedPreferences1.getString("currentLocation", null);
        if(distanceTo != null) {
            try {
                JSONArray jsonArray = new JSONArray(distanceTo);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    copy_lat_location2 = Double.parseDouble(jsonObject.getString("lat"));
                    Log.e("mapLat", "" + copy_lat_location2);
                    copy_lon_location2 = Double.parseDouble(jsonObject.getString("lon"));
                    }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Location locationA = new Location("A");
        locationA.setLatitude(copy_lat_location2);
        Log.e("mapLat2", "" + copy_lat_location2);
        locationA.setLongitude(copy_lon_location2);

        //등록된 마커 위치
        for (int j = 0; j < mapLocationData.size(); j++) {
            Location locationB = new Location("B");
            locationB.setLatitude(mapLocationData.get(j).getLat());
            locationB.setLongitude(mapLocationData.get(j).getLon());
            distance = locationA.distanceTo(locationB);

            distanceArray[j] = Math.round((distance/1000)*100)/100.0;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("distance", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();
        try {
        for(int i = 0; i < mapLocationData.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            //현재 위치와 등록된 마커 사이의 거리를 저장한다.
                jsonObject.put("서대문 형무소 역사관", distanceArray[0]);
                jsonObject.put("독립 기념관", distanceArray[1]);
                jsonObject.put("안성 3.1운동 기념관", distanceArray[2]);
            jsonObject.put("숭실대학교 조만식 기념관", distanceArray[3]);
            jsonObject.put("매헌 윤봉길의사 기념관", distanceArray[4]);
            jsonObject.put("효창공원 백범김구 기념관", distanceArray[5]);
            jsonObject.put("도산 안창호 기념관", distanceArray[6]);
            jsonObject.put("안중근 의사 기념관", distanceArray[7]);
            jsonObject.put("유관순 기념관", distanceArray[8]);
            jsonObject.put("윤봉길의사 기념관", distanceArray[9]);
            jsonObject.put("이봉창 선생 생가", distanceArray[10]);
            jsonObject.put("지청천장군 생가터", distanceArray[11]);
            jsonObject.put("유관순열사 생가터", distanceArray[12]);
            jsonObject.put("박열의사 생가", distanceArray[13]);
            jsonObject.put("신채호선생 생가", distanceArray[14]);
            jsonObject.put("채기중선생 생가", distanceArray[15]);
            jsonObject.put("윤봉길의사 생가", distanceArray[16]);
            jsonArray.put(jsonObject);

        }
            Log.d("JSON Test", jsonArray.toString());
        editor.putString("distance", jsonArray.toString());
        editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return distance;
    }


    //마커가 아닌 지도가 클릭되었을 시 선택되어 있던 마커는 선택되지 않은 것처럼 표현(onclick)
    //null값 삽입
    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }


    private void getIndeMarkerItems() {

        mapLocationData.add(new MapLocationData(37.574484, 126.956092, "서대문 형무소 역사관", "서울 서대문구 통일로 251 서대문형무소역사관"));
        mapLocationData.add(new MapLocationData(36.783582, 127.223201, "독립 기념관", "경기도 안성시 원곡면 만세로 868"));
        mapLocationData.add(new MapLocationData(37.061852, 127.174461, "안성 3.1운동 기념관", "경기도 안성시 원곡면 만세로 868"));
        mapLocationData.add(new MapLocationData(37.471439, 127.036714, "매헌 윤봉길의사 기념관", "서울 서초구 매헌로 99"));
        mapLocationData.add(new MapLocationData(37.496499, 126.956865, "숭실대학교 조만식 기념관", "서울 동작구 상도동 511"));
        mapLocationData.add(new MapLocationData(37.524091, 127.035613, "도산 안창호 기념관", "서울 강남구 도산대로 45길 20"));
        mapLocationData.add(new MapLocationData(37.545238, 126.959742, "효창공원 백범김구 기념관", "서울 용산구 임정로 26"));
        mapLocationData.add(new MapLocationData(37.553960, 126.980528, "안중근 의사 기념관", "서울 중구 소월로 91"));
        mapLocationData.add(new MapLocationData(37.565107, 126.969595, "유관순 기념관", "서울 중구 통일로 4길 30-1"));
        mapLocationData.add(new MapLocationData(36.686589, 126.651461, "윤봉길의사 기념관", "충청남도 예산군 덕산면 덕산온천로 183-5"));

        mapLocationData.add(new MapLocationData(37.539630, 126.961062, "이봉창 선생 생가", "서울 용산구 백범로 285-5"));
        mapLocationData.add(new MapLocationData(37.586522, 126.981876, "지청천장군 생가터", "서울 종로구 삼청동 38-1"));
        mapLocationData.add(new MapLocationData(36.757527, 127.316815, "유관순열사 생가터", "충남 천안시 동남구 병천면 용두리 338-1"));
        mapLocationData.add(new MapLocationData(36.681228, 128.135749, "박열의사 생가", "경북 문경시 마성면 오천리 98"));
        mapLocationData.add(new MapLocationData(36.232837, 127.411114, "신채호선생 생가", "대전 중구 어남동 233"));
        mapLocationData.add(new MapLocationData(36.551238, 128.157353, "채기중선생 생가", "경북 상주시 이안면 소암리 290-1"));
        mapLocationData.add(new MapLocationData(36.685502, 126.652241, "윤봉길의사 생가", "충남 예산군 덕산면 시량리 135"));


        //다중 마커, 지도 위에 뿌리기
        for (MapLocationData data : mapLocationData) {
            addMarker(data, false);

        }

    }

    private Marker addMarker(MapLocationData markerItem, boolean isSelectedMarker) {


        LatLng positionLatLng = new LatLng(markerItem.getLat(), markerItem.getLon());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(markerItem.getName());
        markerOptions.position(positionLatLng);
        markerOptions.snippet(markerItem.getLocation());


        return mMap.addMarker(markerOptions);

    }


    public boolean onMarkerClick(Marker marker) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        changeSelectedMarker(marker);

        return true;
    }


    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }


    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;

        String name = marker.getTitle();
        String location = marker.getTitle();
        MapLocationData temp = new MapLocationData(lat, lon, name, location);
        return addMarker(temp, isSelectedMarker);

    }



    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);


            List<Location> locationList = locationResult.getLocations();
            Log.e("Locationsize", "" + locationList.size());
            //locationList의 생성될때
            if (locationList.size() > 0) {
                //맴다이얼로그에서 받아온 네임 값이 널값
                //locationChecker가 false일때
                Intent intent = getIntent();
                String name = intent.getStringExtra(MapListDialog.NAME);
                    if(locationChecker == false && name == null) {
                        location = locationList.get(locationList.size() - 1);

                        currentPosition
                                = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.e("latlon3", "" + location.getLatitude() + "" + location.getLongitude());

                        String markerTitle = getCurrentAddress(currentPosition);
                        String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                                + " 경도:" + String.valueOf(location.getLongitude());

                        Log.d(TAG, "onLocationResult : " + markerSnippet);

                        //현재 위치에 마커 생성하고 이동
                        setCurrentLocation(location, markerTitle, markerSnippet);
                        mCurrentLocatiion = location;

                        //위도, 경도 복사(복사 후 다음에 0.1만큼 더 이동하면 그때 제 위치로 줌한다);
                        copy_lat_location = location.getLatitude();
                        copy_lon_location = location.getLongitude();

                        //현재 위치 저장
                        SharedPreferences sharedPreferences = getSharedPreferences("distance", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        JSONArray jsonArray = new JSONArray();
                        try {
                            for (int i = 0; i < 1; i++) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("lat", location.getLatitude());
                                jsonObject.put("lon", location.getLongitude());
                                jsonArray.put(jsonObject);
                            }
                            Log.d("JSON Test", jsonArray.toString());
                            editor.putString("currentLocation", jsonArray.toString());
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                locationChecker = true;
//                }
            }
//                else {
//                    location = locationList.get(locationList.size() - 1);
////                location = locationList.get(0);
//
//                    currentPosition
//                            = new LatLng(location.getLatitude(), location.getLongitude());
//
//
//                    String markerTitle = getCurrentAddress(currentPosition);
//                    String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
//                            + " 경도:" + String.valueOf(location.getLongitude());
//
//                    Log.d(TAG, "onLocationResult : " + markerSnippet);
//
//
//                    //현재 위치에 마커 생성하고 이동
//
//                    setCurrentLocation(location, markerTitle, markerSnippet);
//
//                    mCurrentLocatiion = location;
//                    copy_lat_location = location.getLatitude();
//                    copy_lon_location = location.getLongitude();
//
//                    locationChecker = true;
//                }
            }

    };



    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission())
                mMap.setMyLocationEnabled(true);

        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        if (checkPermission()) {

            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            if (mMap!=null)
                mMap.setMyLocationEnabled(true);

        }


    }


    @Override
    protected void onStop() {

        super.onStop();

        if (mFusedLocationClient != null) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }




    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_SHORT).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_SHORT).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_SHORT).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {


        if (currentMarker != null) currentMarker.remove();


        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);


        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);

    }


    public void setDefaultLocation() {

        //독립지도항목에서 클릭 시 넘어온 값이 있을 경우와 없을 경우
        Intent intent = getIntent();
//        int position = intent.getIntExtra(MapListDialog.POSITION, 0);
        String place_name = intent.getStringExtra(MapListDialog.NAME);
//        Log.e("position", "" + position);
        Log.e("place_name", "" + place_name);

        if(place_name != null) {
            //position이 0이 아니면 해당 포지션의 이름을 가진 마커 줌인

            for(int i = 0; i < mapLocationData.size(); i++) {
                if(mapLocationData.get(i).getName().equals(place_name)) {

                    LatLng DEFAULT_LOCATION = new LatLng(mapLocationData.get(i).getLat(), mapLocationData.get(i).getLon());
                    Log.e("getLat", "" + mapLocationData.get(i).getLat());
                    Log.e("getLon", "" + mapLocationData.get(i).getLon());

                    if (currentMarker != null) currentMarker.remove();

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(DEFAULT_LOCATION);
                    markerOptions.draggable(true);

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
                    mMap.moveCamera(cameraUpdate);

                    place_name = null;
                }
            }
        }
        else {


            //디폴트 위치, Seoul
            LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
            String markerTitle = "위치정보 가져올 수 없음";
            String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";


            if (currentMarker != null) currentMarker.remove();

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(DEFAULT_LOCATION);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        currentMarker = mMap.addMarker(markerOptions);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
            mMap.moveCamera(cameraUpdate);
        }

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
            return true;
        }

        return false;

    }



    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates();
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();

                }else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    }).show();
                }
            }

        }
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Map.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d(TAG, "onActivityResult : GPS 활성화 되있음");


                        needRequest = true;

                        return;
                    }
                }

                break;
        }
    }


}
