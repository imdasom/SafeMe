package safeme.com.naver.cafe.safeme.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import safeme.com.naver.cafe.safeme.MainActivity;
import safeme.com.naver.cafe.safeme.R;

public class PhonenumDialog extends DialogFragment implements DialogInterface.OnCancelListener, View.OnClickListener {

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/safeme";
    private File filePath = new File(path);
    private File file = new File(path + "/phonenum.txt");

    private Button btnOK, btnCancel;
    private EditText etPhonenum1, etPhonenum2;
    private String strPhonenum[];
    private boolean saveTag = false;

    private Context mContext;

    public void init(MainActivity context){
        strPhonenum = new String[2];
        strPhonenum[0] = "";
        strPhonenum[1] = "";

        mContext = context;
        if (filePath.exists() && file.exists()) {
            readFile();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View v = inflater.inflate(R.layout.dialog_phonenum, container, false);

        btnOK = (Button) v.findViewById(R.id.btnOK);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnOK.setOnClickListener(PhonenumDialog.this);
        btnCancel.setOnClickListener(PhonenumDialog.this);
        etPhonenum1 = (EditText) v.findViewById(R.id.etPhonenum1);
        etPhonenum2 = (EditText) v.findViewById(R.id.etPhonenum2);

        if (filePath.exists() && file.exists()) {
            readFile();
        }

        if(strPhonenum.length>0){
            if(strPhonenum[0]!=null)
                etPhonenum1.setText(strPhonenum[0].toString());
            if(strPhonenum[1]!=null)
                etPhonenum2.setText(strPhonenum[1].toString());
        }

        return v;
    }

    public String[] getPhonenum() {
        return strPhonenum;
    }

    public void readFile() {
        try {
            byte[] buffer = new byte[50];

            FileInputStream fis = new FileInputStream(file);
            int rc = 0;

            while (true) {
                int readSize = fis.read(buffer, 0, buffer.length);
                if (readSize==-1) break;
                else{
                    rc = rc + readSize;
                }
            }
            fis.close();
            String tempString;
            if(rc>0){
                tempString = new String(buffer, 0, rc);
            }else{
                tempString = "";
            }

            strPhonenum = tempString.split(",");

        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("PHONE_NUMBER", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(strPhonenum.length==0){
            strPhonenum[0] = "";
            strPhonenum[1] = "";
        }else if(strPhonenum.length==1){
            String strPhonenum1 = strPhonenum[0];
            strPhonenum = new String[2];
            strPhonenum[0] = strPhonenum1;
            strPhonenum[1] = "";
            editor.putString("phonenum1", strPhonenum[0]);
            editor.putString("phonenum2", strPhonenum[1]);
        }else{

        }
        editor.putString("phonenum1", strPhonenum[0]);
        editor.putString("phonenum2", strPhonenum[1]);
        editor.commit();
    }

    public void writeFile() {
        try {

            if (filePath.exists() && file.exists()) {

            } else {
                filePath.mkdirs();
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            strPhonenum[0] = etPhonenum1.getText().toString();
            strPhonenum[1] = etPhonenum2.getText().toString();

            fos.write((strPhonenum[0] + "," + strPhonenum[1]).getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("PHONE_NUMBER", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phonenum1", strPhonenum[0]);
        editor.putString("phonenum2", strPhonenum[1]);
        editor.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                saveTag = true;
                writeFile();
                getDialog().cancel();
                break;

            case R.id.btnCancel:
                saveTag = false;
                readFile();
                etPhonenum1.setText(strPhonenum[0]);
                etPhonenum2.setText(strPhonenum[1]);
                getDialog().cancel();
                break;
        }
    }
}