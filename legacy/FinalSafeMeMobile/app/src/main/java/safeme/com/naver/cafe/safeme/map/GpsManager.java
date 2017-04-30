package safeme.com.naver.cafe.safeme.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.video.MyVideoActivity;

/**
 * Created by dasom on 2015-12-08.
 */
public class GpsManager implements Serializable, android.location.LocationListener {

    private static final long serialVersionUID = 1L;

    public Context mContext;
    public LocationManager locationManager;
    public String provider;
    public ProgressDialog waitingdialog = null;
    public MyVideoActivity.MyVideoHandler myVideoHandler;
    public MyMapActivity.MyHandler myHandler;

    public GoogleMap mMap;
    public Marker mMarker;
    public String addr;
    public double lat;
    public double lng;
    public float accuracy;
    public float currentZoomLevel;
    public boolean locationTag;
    public boolean gps_enabled, network_enabled;

    public GpsManager() {
        mContext = null;
        locationManager = null;
        provider = null;

        addr = "";
        lat = 0.0f;
        lng = 0.0f;
        accuracy = 0.0f;
        locationTag = false;

        myVideoHandler = null;
        myHandler = null;

        gps_enabled = false;
        network_enabled = false;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isStartLocation() {
        if (provider != null) return true;
        else return false;
    }

    public boolean initGps() {
        //google play service가 가능한지 여부를 가져와서 가능하지 않다면 오류다이알로그를 띄우고
        //가능하다면 locationManager를 호출하여 provider를 준비합니다.
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null)
            Log.v("locationManager", "locationManager : " + locationManager);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);

        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gps_enabled && !network_enabled) {
            Log.d("safeme", "gps none");
            return false;
        } else {
            /*if (gps_enabled) {
                Log.d("safeme", "gps gps");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
                return true;
            } else {*/
                Log.d("safeme", "gps network");
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, this);
                return true;
            //}
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("LAT_LNG_ADDR", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Toast.makeText(mContext, "onLocationChanged(" + location.getLatitude() + ", " + location.getLongitude() + "), Provider : "+provider, Toast.LENGTH_SHORT).show();
        Log.d("safeme", "onLocationChanged(" + location.getLatitude() + ", " + location.getLongitude() + "), Provider : " + provider);

        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            lat = location.getLatitude();    //경도
            lng = location.getLongitude();         //위도
            accuracy = location.getAccuracy();        //신뢰도
        } else {
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            lat = location.getLatitude();    //경도
            lng = location.getLongitude();         //위도
            accuracy = location.getAccuracy();        //신뢰도
        }

        if (mMap == null) {
            Geocoder coder = new Geocoder(mContext);
            try {
                List<Address> address = coder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    addr = address.get(0).getAddressLine(0).toString();
                }
            } catch (IOException e) {
                addr = "";
                Log.d("safeme", "onlocation gps addr : 주소 획득 실패");
                e.printStackTrace();
            }
        } else {
            if (waitingdialog != null)
                waitingdialog.cancel();

            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            if (mMarker == null && locationTag == false) {
                currentZoomLevel = 16.0f; //맨 처음에는 무조건 16배로 시작
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, currentZoomLevel));
                locationTag = true;
            }
            if (mMarker != null)
                mMarker.remove();

            // 위치를 받아와 마커등록
            mMarker = mMap.addMarker(new MarkerOptions().position(loc).icon(BitmapDescriptorFactory.fromResource(R.drawable.markermypos)));
            if (mMap != null) {
            }

            //Toast.makeText(MyMapActivity.this, "위도  : " + location.getLatitude() + " 경도: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        }

        //Criteria criteria = new Criteria();
        //provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, this);
        //locationManager.requestLocationUpdates(provider, 5000, 1, this);

        editor.putString("lattitude", String.valueOf(lat));
        editor.putString("longitude", String.valueOf(lng));
        editor.putString("address", addr);
        editor.commit();

        if (myVideoHandler != null) {
            Log.d("safeme", "notify myVideoHandler");
            Message msg = new Message();
            msg.what = Constants.ON_LOCATION_CHANGED;
            myVideoHandler.sendMessage(msg);
            myVideoHandler = null;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
