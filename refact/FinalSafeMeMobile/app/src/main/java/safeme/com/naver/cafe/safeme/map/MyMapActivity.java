package safeme.com.naver.cafe.safeme.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.http.HttpConnector;
import safeme.com.naver.cafe.safeme.http.HttpUtils;
import safeme.com.naver.cafe.safeme.http.ProcessCallback;
import safeme.com.naver.cafe.safeme.http.callback.ReceiveFootDataCallback;
import safeme.com.naver.cafe.safeme.http.callback.ReceivePoliceDataCallback;
import safeme.com.naver.cafe.safeme.map.handle.MapActivityHandleCallback;
import safeme.com.naver.cafe.safeme.map.handle.MapActivityHandler;
import safeme.com.naver.cafe.safeme.map.helper.MapActivityUiHelper;
import safeme.com.naver.cafe.safeme.map.util.GoogleMapAdapter;

/**
 * Created by safeme on 2015-12-02.
 * 경찰서와 전자발찌의 위치를 지도에 마킹하는 클래스
 */

public class MyMapActivity extends FragmentActivity/* implements LocationListener*/ {

    private static String TAG = "Safeme/MyMapActivity";

    private GoogleMapAdapter googleMapAdapter;
    private GoogleMap googleMap; // Might be null if Google Play services APK is not available.
    private GpsManager gpsManager;

    private MapActivityHandler handler;
    private MapActivityUiHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initMemberField();

        SupportMapFragment supportMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        googleMapAdapter.setUpMapIfNeeded(supportMapFragment, googleMap);

        initGpsManager();
        setCurrentUserLocation();
        initMapPosition();


        getFootPositionData();
        getPolicePositionData();

        uiHelper.onCreate();
    } //onCreate

    private void initMemberField() {
        MapActivityHandleCallback handleCallback = new MapActivityHandleCallback();
        handleCallback.setActivity(MyMapActivity.this);
        handler = new MapActivityHandler(handleCallback);
        uiHelper = new MapActivityUiHelper(MyMapActivity.this, handler);

        googleMap = null;
        googleMapAdapter = new GoogleMapAdapter();
    }

    private void initGpsManager() {
        gpsManager = new GpsManager();
        gpsManager.setContext(MyMapActivity.this);
        gpsManager.getData().setGoogleMap(googleMap);
        gpsManager.initGps();
        gpsManager.getData().setMapHandler(handler);
    }

    public void setCurrentUserLocation() {
        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lattitude", 0.0f);
        double lng = intent.getDoubleExtra("longitude", 0.0f);
        gpsManager.getData().setLatitude(lat);
        gpsManager.getData().setLongitude(lng);
    }

    private void initMapPosition() {
        if (gpsManager.checkLocationValidation()) {
            gpsManager.moveCameraToCurrentLocation();
            gpsManager.initMarker();

        } else {
            gpsManager.showWaitingDialog();
        }
    }


    private void getFootPositionData() {
        String url = Constants.PARSING_URL + Constants.PARSING_DIR_GETFOOTPOS;
        ProcessCallback callback = new ReceiveFootDataCallback();
        HttpConnector httpConnector = HttpUtils.getHttpConnector(url, callback, handler);
        httpConnector.start();
    }

    private void getPolicePositionData() {
        String url = Constants.PARSING_URL + Constants.PARSING_DIR_GETPOLICEPOS;
        ProcessCallback callback = new ReceivePoliceDataCallback();
        HttpConnector httpConnector = HttpUtils.getHttpConnector(url, callback, handler);
        httpConnector.start();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void drawPoliceMarker(String[][] policepositions, Marker[] policeMarkers){
        //현재 화면 상하좌우의 좌표를 구해와 그 좌표안에 포함된 경찰서의 위치를 구해 AsyncTask를 이용하여 마킹합니다.
        Double[] screenLatLng = new Double[5];
        screenLatLng[0] = googleMap.getProjection().getVisibleRegion().latLngBounds.southwest.latitude;
        screenLatLng[1] = googleMap.getProjection().getVisibleRegion().latLngBounds.southwest.longitude;
        screenLatLng[2] = googleMap.getProjection().getVisibleRegion().latLngBounds.northeast.latitude;
        screenLatLng[3] = googleMap.getProjection().getVisibleRegion().latLngBounds.northeast.longitude;
        for (int i = 0; i < policepositions.length; i++) {
            PoliceMarkerDrawer drawer = new PoliceMarkerDrawer(i);
            drawer.setPolicePositions(policepositions);
            drawer.setPoliceMarkers(policeMarkers);
            drawer.execute(screenLatLng);
        }
    }



    public GpsManager getGpsManager() {
        return gpsManager;
    }

}