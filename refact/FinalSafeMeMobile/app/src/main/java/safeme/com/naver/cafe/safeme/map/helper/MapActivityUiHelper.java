package safeme.com.naver.cafe.safeme.map.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.map.MyMapActivity;

/**
 * Created by dasom on 2017-05-21.
 */
public class MapActivityUiHelper {
    private Context context;
    private Handler handler;
    private TextView logRecord;
    private ImageButton movemypos;

    public MapActivityUiHelper(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;

        MyMapActivity activity = (MyMapActivity) context;
        initTextViews(activity);
        initImageButtons(activity);
    }

    private void initTextViews(Activity activity) {
        logRecord = (TextView) activity.findViewById(R.id.log);
        logRecord.setMovementMethod(new ScrollingMovementMethod());
        logRecord.setVisibility(View.GONE);
    }

    private void initImageButtons(final MyMapActivity activity) {
        movemypos = (ImageButton) activity.findViewById(R.id.movemypos);
        movemypos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activity.getGpsManager().checkLocationValidation()) {
                    activity.getGpsManager().moveCameraToCurrentLocation();
                }
            }
        });
    }

    public void onSetUpMap(double lattidute, double longitude) {
        logRecord.append("setupMap() : " + lattidute + ", " + longitude + "\n");
    }

    public void onCreate() {
        logRecord.append("onCreate");
    }
}
