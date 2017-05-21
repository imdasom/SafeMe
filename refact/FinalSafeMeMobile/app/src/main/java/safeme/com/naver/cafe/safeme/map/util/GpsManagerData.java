package safeme.com.naver.cafe.safeme.map.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Handler;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by dasom on 2017-05-21.
 */
public class GpsManagerData {
    private static final long serialVersionUID = 1L;

    private String provider;
    private ProgressDialog waitingDialog = null;
    private Handler videoHandler;
    private Handler mapHandler;

    private GoogleMap googleMap;
    private Marker marker;
    private String address;
    private double latitude;
    private double longitude;
    private float accuracy;
    private float currentZoomLevel;
    private boolean locationTag;
    private boolean gpsEnabled, networkEnabled;

    private LatLng currentLocation;
    private LatLng latLng;

    public GpsManagerData() {
        resetData();

    }

    public void resetData() {
        provider = null;

        address = "";
        latitude = 0.0f;
        longitude = 0.0f;
        accuracy = 0.0f;
        locationTag = false;

        videoHandler = null;
        mapHandler = null;

        gpsEnabled = false;
        networkEnabled = false;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public ProgressDialog getWaitingDialog() {
        return waitingDialog;
    }

    public void setWaitingDialog(ProgressDialog waitingDialog) {
        this.waitingDialog = waitingDialog;
    }

    public Handler getVideoHandler() {
        return videoHandler;
    }

    public void setVideoHandler(Handler videoHandler) {
        this.videoHandler = videoHandler;
    }

    public Handler getMapHandler() {
        return mapHandler;
    }

    public void setMapHandler(Handler mapHandler) {
        this.mapHandler = mapHandler;
    }

    public GoogleMap getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getCurrentZoomLevel() {
        return currentZoomLevel;
    }

    public void setCurrentZoomLevel(float currentZoomLevel) {
        this.currentZoomLevel = currentZoomLevel;
    }

    public boolean isLocationTag() {
        return locationTag;
    }

    public void setLocationTag(boolean locationTag) {
        this.locationTag = locationTag;
    }

    public boolean isGpsEnabled() {
        return gpsEnabled;
    }

    public void setGpsEnabled(boolean gpsEnabled) {
        this.gpsEnabled = gpsEnabled;
    }

    public boolean isNetworkEnabled() {
        return networkEnabled;
    }

    public void setNetworkEnabled(boolean networkEnabled) {
        this.networkEnabled = networkEnabled;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.currentLocation = currentLocation;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
