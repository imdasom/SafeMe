package safeme.com.naver.cafe.safeme.map.handle;

import android.app.Activity;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;

import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.map.MyMapActivity;

/**
 * Created by dasom on 2017-05-21.
 */
public class MapActivityHandleCallback implements MapActivityListener {
    private MyMapActivity activity;

    public void setActivity(Activity activity) {
        this.activity = (MyMapActivity) activity;
    }

    @Override
    public void onRecievedPolicePositions(Message message) {
        String[][] policepositions = (String[][]) message.obj;
        Marker[] policeMarkers = new Marker[policepositions.length];
        for (int i = 0; i < policepositions.length; i++) {
            policeMarkers[i] = null;
        }
        activity.drawPoliceMarker(policepositions, policeMarkers);
    }

    @Override
    public void onRecievedFootPositions(Message message) {
        String[][] footPositions = (String[][]) message.obj;
        for (int i = 0; i < footPositions.length; i++) {
            activity.setUpMap(Double.parseDouble(footPositions[i][1]),
                    Double.parseDouble(footPositions[i][2]),
                    footPositions[i][0], R.drawable.markerfoot);
        }
    }

    @Override
    public void onParsingError(Message message) {
        Log.d("safeme", "parsing_error");
        activity.finish();
    }

    @Override
    public void oRemovedMarker(Message message) {
        if(activity.getMarket()!=null)
            activity.getMarket().remove();
    }
}
