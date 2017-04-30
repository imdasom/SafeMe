package safeme.com.naver.cafe.safeme.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import safeme.com.naver.cafe.safeme.MainActivity;
import safeme.com.naver.cafe.safeme.R;

/**
 * Created by dasom on 2015-12-13.
 */
public class MySettingsListAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    ArrayList<String> mArrayList;
    int layout;

    public MySettingsListAdapter(MainActivity mContext,
                            int layout, ArrayList<String> arrSettings) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.layout = layout;
        this.mArrayList = arrSettings;
        mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final int pos = position;
        if(convertView == null){
            convertView = mInflater.inflate(layout, parent, false);
        }
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView1);
        TextView textView = (TextView)convertView.findViewById(R.id.textView1);
        switch(position){
            case 0:
                imageView.setImageResource(R.drawable.editphonenum);
                textView.setText(mArrayList.get(0));
                break;
            case 1:
                imageView.setImageResource(R.drawable.setlockscreen);
                textView.setText(mArrayList.get(1));
                break;
        }

        return convertView;
    }

}
