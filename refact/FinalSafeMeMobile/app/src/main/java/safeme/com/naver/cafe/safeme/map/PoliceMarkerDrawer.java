package safeme.com.naver.cafe.safeme.map;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;

import safeme.com.naver.cafe.safeme.R;

/**
 * Created by dasom on 2017-05-21.
 */
public class PoliceMarkerDrawer extends AsyncTask<Double, Void, Boolean> {
    int i;
    String[][] policePositions;
    Marker[] policeMarkers;

    public PoliceMarkerDrawer(int id) {
        i = id;
    }

    public void setPolicePositions(String[][] policePositions) {
        this.policePositions = policePositions;
    }

    public void setPoliceMarkers(Marker[] policeMarkers) {
        this.policeMarkers = policeMarkers;
    }

    @Override
    protected Boolean doInBackground(Double... params) {
        Log.d("safeme", "어싱크타스크 doInBackground : " + params[0] + ", " + params[1] + ", " + params[2] + ", " + params[3]);
        Boolean result = false;
        try{
            if (((Double.parseDouble(policePositions[i][1]) - params[0]) > 0.0) && (params[2] - (Double.parseDouble(policePositions[i][1])) > 0.0)) {
                if (((Double.parseDouble(policePositions[i][2]) - params[1]) > 0.0) && (params[3] - (Double.parseDouble(policePositions[i][2])) > 0.0)) {
                    if (policeMarkers[i] == null) {
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

            /*Log.d("safeme", "화면에 보이는 위경도"+policepos.length+" : " + googleMap.getProjection().getVisibleRegion().latLngBounds.northeast.latitude
                    +", "+googleMap.getProjection().getVisibleRegion().latLngBounds.northeast.longitude
                    +", "+googleMap.getProjection().getVisibleRegion().latLngBounds.southwest.latitude
                    +", "+googleMap.getProjection().getVisibleRegion().latLngBounds.southwest.longitude);*/

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
