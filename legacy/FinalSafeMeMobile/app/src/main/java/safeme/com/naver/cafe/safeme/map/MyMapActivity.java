package safeme.com.naver.cafe.safeme.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.parser.JSONReciever;

/**
 * Created by safeme on 2015-12-02.
 * 경찰서와 전자발찌의 위치를 지도에 마킹하는 클래스
 */

public class MyMapActivity extends FragmentActivity/* implements LocationListener*/ {

    private static String TAG = "Safeme/MyMapActivity";

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GpsManager gpsManager;

    private TextView logRecord = null;
    private LatLng loc = null;

    private Marker mMarker;
    private String[][] policepos = null;
    private Marker[] policeMarker = null;
    private String[][] footpos = null;

    private ProgressDialog waitingdialog;

    private MyHandler mHandler = new MyHandler();

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.GET_POLICEPOS_DATA:
                    policepos = (String[][]) msg.obj;
                    policeMarker = new Marker[policepos.length];
                    for (int i = 0; i < policepos.length; i++) {
                        policeMarker[i] = null;
                        logRecord.append(policepos[i][0] + ", " + policepos[i][1] + ", " + policepos[i][2] + "\n");
                    }
                    drawPoliceMarker();
                    break;
                case Constants.GET_FOOTPOS_DATA:
                    footpos = (String[][]) msg.obj;
                    for (int i = 0; i < footpos.length; i++) {
                        setUpMap(Double.parseDouble(footpos[i][1]),
                                Double.parseDouble(footpos[i][2]),
                                footpos[i][0], R.drawable.markerfoot);
                        logRecord.append(footpos[i][0] + ", " + footpos[i][1] + ", " + footpos[i][2] + "\n");
                    }
                    break;

                case Constants.PARSING_ERROR:
                    Log.d("safeme", "parsing_error");
                    finish();
                    break;

                case Constants.REMOVE_MARKER:
                    if(mMarker!=null)
                        mMarker.remove();
                    break;
            }
        }
    }

    public void waitingProgress() {
        waitingdialog = ProgressDialog.show(this, "위치 설정중", "잠시만 기다려주세요.", true);
        waitingdialog.setCancelable(false);
        gpsManager.waitingdialog = waitingdialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setUpMapIfNeeded();

        gpsManager = new GpsManager();
        gpsManager.mContext = MyMapActivity.this;
        gpsManager.mMap = mMap;
        gpsManager.mMarker = null;
        gpsManager.initGps();
        gpsManager.myHandler = mHandler;

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("lattitude", 0.0f);
        double lng = intent.getDoubleExtra("longitude", 0.0f);
        gpsManager.lat = lat;
        gpsManager.lng = lng;

        if (lat > 0.0f && lng > 0.0f) {
            loc = new LatLng(lat, lng);
            gpsManager.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            gpsManager.mMarker = mMap.addMarker(new MarkerOptions().position(loc).icon(BitmapDescriptorFactory.fromResource(R.drawable.markermypos)));
        } else {
            waitingProgress();
        }

        logRecord = (TextView) findViewById(R.id.log);
        logRecord.setMovementMethod(new ScrollingMovementMethod());
        logRecord.setVisibility(View.GONE);

        ImageButton movemypos = (ImageButton) findViewById(R.id.movemypos);
        movemypos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (gpsManager.lat > 0.0 && gpsManager.lng > 0.0)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsManager.lat, gpsManager.lng), gpsManager.currentZoomLevel));
            }
        });

        // 먼저 전자발찌의 위치를 받아옵니다.
        JSONReciever footposReciever = new JSONReciever();
        footposReciever.setHandler(mHandler);
        footposReciever.setUrl(Constants.PARSING_URL + Constants.PARSING_DIR_GETFOOTPOS);
        footposReciever.setParseType(Constants.GET_FOOTPOS_DATA);
        footposReciever.start();

        // 다음으로 경찰서의 위치를 받아옵니다.
        JSONReciever policeposReciever = new JSONReciever();
        policeposReciever.setHandler(mHandler);
        policeposReciever.setUrl(Constants.PARSING_URL + Constants.PARSING_DIR_GETPOLICEPOS);
        policeposReciever.setParseType(Constants.GET_POLICEPOS_DATA);
        policeposReciever.start();

        logRecord.append("onCreate()\n");
    }//onCreate

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void drawPoliceMarker(){
        //현재 화면 상하좌우의 좌표를 구해와 그 좌표안에 포함된 경찰서의 위치를 구해 AsyncTask를 이용하여 마킹합니다.
        Double[] screenLatLng = new Double[5];
        screenLatLng[0] = mMap.getProjection().getVisibleRegion().latLngBounds.southwest.latitude;
        screenLatLng[1] = mMap.getProjection().getVisibleRegion().latLngBounds.southwest.longitude;
        screenLatLng[2] = mMap.getProjection().getVisibleRegion().latLngBounds.northeast.latitude;
        screenLatLng[3] = mMap.getProjection().getVisibleRegion().latLngBounds.northeast.longitude;
        for (int i = 0; i < policepos.length; i++) {
            new drawPoliceMarker(i).execute(screenLatLng);
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    //초기에 한번 실행되는데 여기서 경찰서 위치를 초기화하고 마커를 표시하도록 하였습니다.
    //이 함수가 실행되면 경찰서의 위치가 초기화된 상태가 됩니다.
    //이 함수를 개선해서 반경 Xkm만큼에 있는 경찰서만 초기화하도록 할 예정입니다.
    private void setUpMapIfNeeded() {
        //logRecord.append("setUpMapIfNeeded()\n");
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition pos) {
                    if (pos.zoom != gpsManager.currentZoomLevel) {
                        // do you action here
                        gpsManager.currentZoomLevel = pos.zoom;
                    }
                    if (policepos != null) {
                        /*//현재 화면 상하좌우의 좌표를 구해와 그 좌표안에 포함된 경찰서의 위치를 구해 AsyncTask를 이용하여 마킹합니다.
                        Double[] screenLatLng = new Double[5];
                        screenLatLng[0] = mMap.getProjection().getVisibleRegion().latLngBounds.southwest.latitude;
                        screenLatLng[1] = mMap.getProjection().getVisibleRegion().latLngBounds.southwest.longitude;
                        screenLatLng[2] = mMap.getProjection().getVisibleRegion().latLngBounds.northeast.latitude;
                        screenLatLng[3] = mMap.getProjection().getVisibleRegion().latLngBounds.northeast.longitude;
                        for (int i = 0; i < policepos.length; i++) {
                            new drawPoliceMarker(i).execute(screenLatLng);
                        }*/
                        drawPoliceMarker();
                    }
                }
            });
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

            }
        }
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap(double lattidute, double longitude, String label, int icon) {
        logRecord.append("setupMap() : " + lattidute + ", " + longitude + "\n");
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(lattidute, longitude));
        marker.title(label);
        marker.draggable(true);
        marker.icon(BitmapDescriptorFactory.fromResource(icon));
        mMap.addMarker(marker);
    }

    private void setUpMap(Marker marker, double lattidute, double longitude, String label, int icon) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lattidute, longitude));
        markerOptions.title(label);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(icon));
        marker = mMap.addMarker(markerOptions);
    }

    private class drawPoliceMarker extends AsyncTask<Double, Void, Boolean> {
        int i;

        public drawPoliceMarker(int id) {
            i = id;
        }

        @Override
        protected Boolean doInBackground(Double... params) {
            Log.d("safeme", "어싱크타스크 doInBackground : " + params[0] + ", " + params[1] + ", " + params[2] + ", " + params[3]);
            Boolean result = false;
            try{
                if (((Double.parseDouble(policepos[i][1]) - params[0]) > 0.0) && (params[2] - (Double.parseDouble(policepos[i][1])) > 0.0)) {
                    if (((Double.parseDouble(policepos[i][2]) - params[1]) > 0.0) && (params[3] - (Double.parseDouble(policepos[i][2])) > 0.0)) {
                        if (policeMarker[i] == null) {
                            //Log.d("safeme", "화면에 보이는 위경도 좌표 : " + policepos[i][1] + ", " + policepos[i][2]);
                            result = true;
                        }
                    } else {
                        result = false;
                    }
                } else {
                    result = false;
                }
            }catch(NumberFormatException e){

            }

            /*Log.d("safeme", "화면에 보이는 위경도"+policepos.length+" : " + mMap.getProjection().getVisibleRegion().latLngBounds.northeast.latitude
                    +", "+mMap.getProjection().getVisibleRegion().latLngBounds.northeast.longitude
                    +", "+mMap.getProjection().getVisibleRegion().latLngBounds.southwest.latitude
                    +", "+mMap.getProjection().getVisibleRegion().latLngBounds.southwest.longitude);*/

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //Log.d("safeme", "어싱크타스크 onPostExecute");
            if (result) {
                setUpMap(policeMarker[i], Double.parseDouble(policepos[i][1]),
                        Double.parseDouble(policepos[i][2]),
                        policepos[i][3], R.drawable.markerpolice);
            } else {
                if(policeMarker[i]!=null){
                    policeMarker[i].remove();
                    policeMarker[i] = null;
                }
            }
        }
    }

}