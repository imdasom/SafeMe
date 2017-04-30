package safeme.com.naver.cafe.safeme.dialog;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import safeme.com.naver.cafe.safeme.MainActivity;
import safeme.com.naver.cafe.safeme.R;

/**
 * Created by dasom on 2015-12-13.
 */
public class SettingsDialog extends DialogFragment {
    int mNum;
    int mStackLevel = 0;
    private MainActivity mContext;
    private ArrayList<String> arrSettings;
    private MySettingsListAdapter mSetListAdapter;
    private PhonenumDialog pDialog;
    private SetLockScreenDialog rDialog;

    public void setContext(MainActivity mContext){
        this.mContext = mContext;
    }

    public void init(){
        arrSettings = new ArrayList<String>();
        arrSettings.add("비상전화번호 설정");
        arrSettings.add("잠금화면 설정");

        pDialog = new PhonenumDialog();
        pDialog.init(mContext);

        rDialog = new SetLockScreenDialog();
        rDialog.init(mContext);

        mSetListAdapter = new MySettingsListAdapter(mContext, R.layout.listview_settings, arrSettings);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_settings, container, false);
        ListView listView = (ListView)v.findViewById(R.id.listView_settings);

        listView.setAdapter(mSetListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        pDialog.show(getFragmentManager(), "phonenum_dialog");
                        break;
                    case 1:
                        rDialog.show(getFragmentManager(), "settinglockscreen_dialog");
                        break;
                }
            }
        });

        return v;
    }

    public String[] getPhonenum(){
        return pDialog.getPhonenum();
    }


}
