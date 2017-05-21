package safeme.com.naver.cafe.safeme.map.util;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by dasom on 2017-05-21.
 */
public class GoogleMapAdapter {

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #googleMap} is not null.
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
    // 초기에 한번 실행되는데 여기서 경찰서 위치를 초기화하고 마커를 표시하도록 하였습니다.
    // 이 함수가 실행되면 경찰서의 위치가 초기화된 상태가 됩니다.
    // 이 함수를 개선해서 반경 Xkm만큼에 있는 경찰서만 초기화하도록 하였습니다.
    public void setUpMapIfNeeded(SupportMapFragment supportMapFragment, GoogleMap googleMap) {
        if (googleMap == null) {
            googleMap = supportMapFragment.getMap();
            googleMap.setOnCameraChangeListener(onCameraChangeListener);
        }
    }

    private GoogleMap.OnCameraChangeListener onCameraChangeListener = new GoogleMap.OnCameraChangeListener() {

        @Override
        public void onCameraChange(CameraPosition pos) {
            if (pos.zoom != gpsManager.getCurrentZoomLevel()) {
                gpsManager.setCurrentZoomLevel(pos.zoom);
            }

//            if (policepos != null) {
//                drawPoliceMarker(policepos, null);
//            }
        }
    };

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #googleMap} is not null.
     */
    public void setUpMap(double lattidute, double longitude, String label, int icon) {
        uiHelper.onSetUpMap(lattidute, longitude);

        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(lattidute, longitude));
        marker.title(label);
        marker.draggable(true);
        marker.icon(BitmapDescriptorFactory.fromResource(icon));
        googleMap.addMarker(marker);
    }

    public void setUpMap(Marker marker, double lattidute, double longitude, String label, int icon) {
        uiHelper.onSetUpMap(lattidute, longitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lattidute, longitude));
        markerOptions.title(label);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(icon));
        marker = googleMap.addMarker(markerOptions);
    }
}
