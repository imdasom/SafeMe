package safeme.com.naver.cafe.safeme.dialog;


import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import safeme.com.naver.cafe.safeme.MainActivity;
import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.lockscreen.LockScreenService;

/**
 * Created by dasom on 2015-12-13.
 */
public class SetLockScreenDialog extends DialogFragment implements View.OnClickListener {
    int mNum;
    int mStackLevel = 0;
    private Context mContext;
    private Button buttonSet, buttonNoSet;
    private int curSetting; // 0 is false, 1 is true
    private boolean isSet;


    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/safeme";
    private File filePath = new File(path);
    private File file = new File(path + "/lockscreen_setting.txt");

    public void init(MainActivity mContext){
        this.mContext = mContext;
        curSetting = 0;
        isSet = false;

        if (filePath.exists() && file.exists()) {
            readFile();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_lockscreensetting, container, false);
        buttonSet = (Button)v.findViewById(R.id.set);
        buttonNoSet = (Button)v.findViewById(R.id.noset);
        buttonSet.setOnClickListener(this);
        buttonNoSet.setOnClickListener(this);

        if (filePath.exists() && file.exists()) {
            readFile();
        }

        if(curSetting == 1){
            isSet = true;
            buttonSet.setBackgroundResource(R.drawable.button_green);
            buttonNoSet.setBackgroundResource(R.drawable.button_gray);
            getDialog().getContext().startService(new Intent(getDialog().getContext(), LockScreenService.class));
        }else{
            isSet = false;
            buttonSet.setBackgroundResource(R.drawable.button_gray);
            buttonNoSet.setBackgroundResource(R.drawable.button_red);
            getDialog().getContext().stopService(new Intent(getDialog().getContext(), LockScreenService.class));
        }

        return v;
    }

    public void readFile() {
        try {
            byte[] buffer = new byte[50];

            FileInputStream fis = new FileInputStream(file);
            int rc = 0;

            while (true) {
                rc = fis.read(buffer, 0, buffer.length);
                if (rc > 0) break;
            }
            fis.close();
            String tempString = new String(buffer, 0, rc);
            tempString.replace("\n", "");
            if(tempString.equals("0")) {
                curSetting = 0;
            }else if(tempString.equals("1")){
                curSetting = 1;
            }else{
                curSetting = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void writeFile() {
        try {

            if (filePath.exists() && file.exists()) {

            } else {
                filePath.mkdirs();
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);

            if(curSetting == 1){
                curSetting = 1;
                fos.write(("1").getBytes());
            }else {
                curSetting = 0;
                fos.write(("0").getBytes());
            }

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = getDialog().getContext().getSharedPreferences("LOCKSCREEN_SETTING", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("curSetting", String.valueOf(curSetting));
        editor.commit();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        writeFile();
        super.onCancel(dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set:
                curSetting = 1;
                buttonSet.setBackground(getResources().getDrawable(
                        R.drawable.button_green));
                buttonNoSet.setBackground(getResources().getDrawable(
                        R.drawable.button_gray));
                getDialog().getContext().startService(new Intent(mContext, LockScreenService.class));
                break;

            case R.id.noset:
                curSetting = 0;
                buttonSet.setBackground(getResources().getDrawable(
                        R.drawable.button_gray));
                buttonNoSet.setBackground(getResources().getDrawable(
                        R.drawable.button_red));
                getDialog().getContext().stopService(new Intent(mContext, LockScreenService.class));
                break;
        }
    }
}
