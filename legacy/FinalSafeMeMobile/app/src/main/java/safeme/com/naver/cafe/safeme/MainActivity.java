package safeme.com.naver.cafe.safeme;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.dialog.PhonenumDialog;
import safeme.com.naver.cafe.safeme.dialog.SettingsDialog;
import safeme.com.naver.cafe.safeme.guidebook.GuidebookActivity;
import safeme.com.naver.cafe.safeme.lockscreen.LockScreenService;
import safeme.com.naver.cafe.safeme.map.GetPoliceLatLng;
import safeme.com.naver.cafe.safeme.map.GpsManager;
import safeme.com.naver.cafe.safeme.map.MyMapActivity;
import safeme.com.naver.cafe.safeme.parser.JSONReciever;
import safeme.com.naver.cafe.safeme.siren.MySirenActivity;
import safeme.com.naver.cafe.safeme.video.MyVideoActivity;

public class MainActivity extends Activity implements View.OnClickListener {

    public String context = Context.LOCATION_SERVICE;
    public GpsManager gpsManager;

    SettingsDialog sDialog;
    private ImageButton settings;

    public String strPhonenum1;
    public String strPhonenum2;

    public AlertDialog dialog;
    public final int GPS_DIALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SplashActivity.class));

        ImageButton menu0 = (ImageButton) findViewById(R.id.menu0);
        menu0.setOnClickListener(this);

        ImageButton menu1 = (ImageButton) findViewById(R.id.menu1);
        menu1.setOnClickListener(this);

        ImageButton menu2 = (ImageButton) findViewById(R.id.menu2);
        menu2.setOnClickListener(this);

        ImageButton menu3 = (ImageButton) findViewById(R.id.menu3);
        menu3.setOnClickListener(this);

        initializeSetting();

        gpsManager = new GpsManager();
        gpsManager.setContext(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = this.getSharedPreferences("DIRECT_TO_VIDEO", 0);
        boolean directToVideo = sharedPreferences.getBoolean("directToVideo", false);

        sDialog = new SettingsDialog();
        sDialog.setContext(MainActivity.this);
        sDialog.init();

        int googlePlayServiceResult = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (googlePlayServiceResult != ConnectionResult.SUCCESS) {
            GooglePlayServicesUtil.getErrorDialog(googlePlayServiceResult, this, 0, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            }).show();
        } else {
            if (gpsManager == null) {
                gpsManager = new GpsManager();
                gpsManager.setContext(getApplicationContext());
                gpsManager.initGps();
            }
            if(!gpsManager.gps_enabled && !gpsManager.network_enabled){
                boolean result = gpsManager.initGps();
                if(directToVideo){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("directToVideo", false);
                    editor.commit();
                    Intent intent = new Intent(this, MyVideoActivity.class);
                    intent.putExtra("lattitude", gpsManager.lat);
                    intent.putExtra("longitude", gpsManager.lng);
                    intent.putExtra("address", gpsManager.addr);
                    strPhonenum1 = sDialog.getPhonenum()[0];
                    strPhonenum2 = sDialog.getPhonenum()[1];
                    intent.putExtra("phonenum1", strPhonenum1);
                    intent.putExtra("phonenum2", strPhonenum2);
                    startActivity(intent);
                }else{
                    if (!result) {
                        showDialog(GPS_DIALOG);
                    }
                }


            }else{
                //gpsManager.initGps();
            }
        }



    }


    public void initializeSetting() {
        // settings 관련 초기화
        settings = (ImageButton) findViewById(R.id.settings);
        settings.setOnClickListener(this);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case GPS_DIALOG:
                dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("안내")
                        .setMessage("safeme앱 사용시 위치를 \n반드시 활성화 하셔야합니다. \n설정을 해주시기 바랍니다.")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog.cancel();
                            }
                        }).create();
                return dialog;
        }
        return super.onCreateDialog(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.menu0:
                intent = new Intent(this, MyVideoActivity.class);
                intent.putExtra("lattitude", gpsManager.lat);
                intent.putExtra("longitude", gpsManager.lng);
                intent.putExtra("address", gpsManager.addr);
                strPhonenum1 = sDialog.getPhonenum()[0];
                strPhonenum2 = sDialog.getPhonenum()[1];
                intent.putExtra("phonenum1", strPhonenum1);
                intent.putExtra("phonenum2", strPhonenum2);
                startActivity(intent);
                break;
            case R.id.menu1:
                intent = new Intent(this, MyMapActivity.class);
                intent.putExtra("lattitude", gpsManager.lat);
                intent.putExtra("longitude", gpsManager.lng);
                intent.putExtra("address", gpsManager.addr);
                gpsManager = null;
                startActivity(intent);
                break;
            case R.id.menu2:
                startActivity(new Intent(this, GuidebookActivity.class));
                break;
            case R.id.menu3:
                startActivity(new Intent(this, MySirenActivity.class));
                break;
            case R.id.settings:
                sDialog.show(getFragmentManager(), "setting_dialog");
                break;
        }
    }
}
