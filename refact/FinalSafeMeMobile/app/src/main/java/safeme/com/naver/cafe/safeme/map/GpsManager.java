package safeme.com.naver.cafe.safeme.map;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.map.dialog.DialogFactory;
import safeme.com.naver.cafe.safeme.map.util.GpsManagerData;

/**
 * Created by dasom on 2015-12-08.
 */
public class GpsManager implements Serializable, android.location.LocationListener {

    private Context context;
    private LocationManager locationManager;
    private GpsManagerData data;

    public GpsManager() {
        this.data = new GpsManagerData();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public GpsManagerData getData() {
        return this.data;
    }

    public boolean initGps() {
        //google play service가 가능한지 여부를 가져와서 가능하지 않다면 오류다이알로그를 띄우고
        //가능하다면 locationManager를 호출하여 provider를 준비합니다.
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        data.setProvider(locationManager.getBestProvider(criteria, true));

        if (checkGpsStatus()) {
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

    private boolean checkGpsStatus() {
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return (!gpsEnabled && !networkEnabled);
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("safeme", "onLocationChanged(" + location.getLatitude() + ", " + location.getLongitude() + "), Provider : " + data.getProvider());

        setLocationData(location);

        if (data.getGoogleMap() == null) {
            setAddressFromLocation();

        } else {
            if (data.getWaitingDialog() != null) {
                data.setAddress("");
                Log.d("safeme", "onlocation gps addr : 주소 획득 실패");
            }

            if (data.getMarker() == null && data.isLocationTag() == false) {
                moveCameraToCurrentLocation();
                data.setLocationTag(true);
            }

            changeMarker();
        }

        //Criteria criteria = new Criteria();
        //provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, this);
        //locationManager.requestLocationUpdates(provider, 5000, 1, this);

        editSharedPreferencesData();
        sendVideoHandlerIfNotNull();
    }

    private void sendVideoHandlerIfNotNull() {
        if (data.getVideoHandler() != null) {
            Log.d("safeme", "notify videoHandler");
            Message msg = new Message();
            msg.what = Constants.ON_LOCATION_CHANGED;
            data.getVideoHandler().sendMessage(msg);
            data.setVideoHandler(null);
        }
    }

    private void editSharedPreferencesData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LAT_LNG_ADDR", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("lattitude", String.valueOf(data.getLatitude()));
        editor.putString("longitude", String.valueOf(data.getLongitude()));
        editor.putString("address", data.getAddress());
        editor.commit();
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

    public void moveCameraToCurrentLocation() {
        data.getGoogleMap().moveCamera(CameraUpdateFactory.newLatLngZoom(data.getLatLng(), 16.0f));
    }

    public boolean checkLocationValidation() {
        if (data.getLatitude() > 0.0 && data.getLongitude() > 0.0) {
            return true;
        } else {
            return false;
        }
    }

    public void initMarker() {
        MarkerOptions markerOptions = new MarkerOptions().position(data.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.markermypos));
        Marker marker = data.getGoogleMap().addMarker(markerOptions);
        data.setMarker(marker);
    }

    public void changeMarker() {
        if (data.getMarker() != null) {
            data.getMarker().remove();
        }

        // 위치를 받아와 마커등록
        MarkerOptions markerOptions = new MarkerOptions().position(data.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.markermypos));
        Marker marker = data.getGoogleMap().addMarker(markerOptions);
        data.setMarker(marker);
    }

    public void showWaitingDialog() {
        data.setWaitingDialog(DialogFactory.getWaitingDialog(context));
    }

    public void setAddressFromLocation() {
        try {

            Geocoder coder = new Geocoder(context);
            List<Address> address = null;
            address = coder.getFromLocation(data.getLatitude(), data.getLongitude(), 1);


            if (address != null && address.size() > 0) {
                data.setAddress(address.get(0).getAddressLine(0).toString());
            }

        } catch (IOException e) {
            data.setAddress("");
            Log.d("safeme", "onlocation gps addr : 주소 획득 실패");
            e.printStackTrace();
        }
    }

    public void setLocationData(Location location) {
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            data.setLatitude(location.getLatitude());    //경도
            data.setLongitude(location.getLongitude());         //위도
            data.setAccuracy(location.getAccuracy());        //신뢰도
        } else {
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            data.setLatitude(location.getLatitude());    //경도
            data.setLongitude(location.getLongitude());         //위도
            data.setAccuracy(location.getAccuracy());        //신뢰도
        }
    }
}
