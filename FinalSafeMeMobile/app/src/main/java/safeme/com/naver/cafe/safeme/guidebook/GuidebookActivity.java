package safeme.com.naver.cafe.safeme.guidebook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import safeme.com.naver.cafe.safeme.R;


/**
 * Created by safeme on 2015-12-02.
 */
public class GuidebookActivity extends Activity implements View.OnClickListener{
    private Dialog dialog;

    private final static int DIALOG_SUBWAY = 0;
    private final static int DIALOG_TAXI = 1;
    private final static int DIALOG_WOMAN = 2;
    private final static int DIALOG_KIDS = 3;
    private EditText editText;
    private Button cancelSubway, cancelTaxi, cancelWoman, cancelKids;

    private Dialog dialog_subway, dialog_taxi, dialog_woman, dialog_kids;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidebook);

        ImageButton btnSubway = (ImageButton)findViewById(R.id.subway);
        ImageButton btnTaxi = (ImageButton)findViewById(R.id.taxi);
        ImageButton btnWoman = (ImageButton)findViewById(R.id.woman);
        ImageButton btnKids = (ImageButton)findViewById(R.id.kids);
        btnSubway.setOnClickListener(this);
        btnTaxi.setOnClickListener(this);
        btnWoman.setOnClickListener(this);
        btnKids.setOnClickListener(this);

        createDialog(DIALOG_SUBWAY);
        createDialog(DIALOG_TAXI);
        createDialog(DIALOG_WOMAN);
        createDialog(DIALOG_KIDS);
    }

    protected Dialog createDialog(int id) {
        TextView textView1, textView2, textView3, textView4, textView5, textView6;
        switch (id){
            case DIALOG_SUBWAY:
                dialog_subway = new Dialog(this);
                dialog_subway.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_subway.setContentView(R.layout.guidebook_subway);
                TextView tVsubway1= (TextView)dialog_subway.findViewById(R.id.guide_subway1);
                tVsubway1.setOnClickListener(this);
                TextView tVsubway2 = (TextView)dialog_subway.findViewById(R.id.guide_subway2);
                tVsubway2.setOnClickListener(this);
                TextView tVsubway3 = (TextView)dialog_subway.findViewById(R.id.guide_subway3);
                tVsubway3.setOnClickListener(this);
                TextView tVsubway4 = (TextView)dialog_subway.findViewById(R.id.guide_subway4);
                tVsubway4.setOnClickListener(this);
                cancelSubway = (Button)dialog_subway.findViewById(R.id.cancelSubway);
                cancelSubway.setOnClickListener(this);
                break;

            case DIALOG_TAXI:
                dialog_taxi = new Dialog(this);
                dialog_taxi.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_taxi.setContentView(R.layout.guidebook_taxi);
                textView1= (TextView)dialog_taxi.findViewById(R.id.guide_taxi1);
                textView1.setOnClickListener(this);
                editText = (EditText) dialog_taxi.findViewById(R.id.guide_editText1);
                cancelTaxi = (Button)dialog_taxi.findViewById(R.id.cancelTaxi);
                cancelTaxi.setOnClickListener(this);
                break;

            case DIALOG_WOMAN:
                dialog_woman = new Dialog(this);
                dialog_woman.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_woman.setContentView(R.layout.guidebook_woman);
                textView1 = (TextView)dialog_woman.findViewById(R.id.guide_woman1);
                textView1.setOnClickListener(this);
                textView2 = (TextView)dialog_woman.findViewById(R.id.guide_woman2);
                textView2.setOnClickListener(this);
                textView3 = (TextView)dialog_woman.findViewById(R.id.guide_woman3);
                textView3.setOnClickListener(this);
                textView4 = (TextView)dialog_woman.findViewById(R.id.guide_woman4);
                textView4.setOnClickListener(this);
                textView5 = (TextView)dialog_woman.findViewById(R.id.guide_woman5);
                textView5.setOnClickListener(this);
                textView6 = (TextView)dialog_woman.findViewById(R.id.guide_woman6);
                textView6.setOnClickListener(this);
                cancelWoman = (Button)dialog_woman.findViewById(R.id.cancelWoman);
                cancelWoman.setOnClickListener(this);
                break;

            case DIALOG_KIDS:
                dialog_kids = new Dialog(this);
                dialog_kids.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_kids.setContentView(R.layout.guidebook_kids);
                textView1= (TextView)dialog_kids.findViewById(R.id.guide_kids1);
                textView1.setOnClickListener(this);
                textView2 = (TextView)dialog_kids.findViewById(R.id.guide_kids2);
                textView2.setOnClickListener(this);
                textView3 = (TextView)dialog_kids.findViewById(R.id.guide_kids3);
                textView3.setOnClickListener(this);
                cancelKids = (Button)dialog_kids.findViewById(R.id.cancelKids);
                cancelKids.setOnClickListener(this);
                break;
        }
        return dialog;
    }/*

    protected void prepareDialog(int id) {
        switch (id){
            case DIALOG_SUBWAY:
                showDialog = (Button)dialog_subway.findViewById(R.id.btsubway);
                showDialog.setOnClickListener(this);
                break;

            case DIALOG_TAXI:
                showDialog = (Button)dialog_taxi.findViewById(R.id.btsubway);
                showDialog.setOnClickListener(this);
                break;

            case DIALOG_WOMAN:
                showDialog = (Button)dialog_woman.findViewById(R.id.btsubway);
                showDialog.setOnClickListener(this);
                break;

            case DIALOG_KIDS:
                showDialog = (Button)dialog_kids.findViewById(R.id.btsubway);
                showDialog.setOnClickListener(this);
                break;
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelSubway:
                dialog_subway.cancel();
                break;

            case R.id.cancelTaxi:
                dialog_taxi.cancel();
                break;

            case R.id.cancelWoman:
                dialog_woman.cancel();
                break;

            case R.id.cancelKids:
                dialog_kids.cancel();
                break;

            case R.id.subway:
                //prepareDialog(DIALOG_SUBWAY);
                dialog_subway.show();
                break;

            case R.id.taxi:
                //prepareDialog(DIALOG_TAXI);
                dialog_taxi.show();
                break;

            case R.id.woman:
                //prepareDialog(DIALOG_WOMAN);
                dialog_woman.show();
                break;

            case R.id.kids:
                //prepareDialog(DIALOG_KIDS);
                dialog_kids.show();
                break;

            case R.id.guide_subway1:
                parseMessage(Uri.parse("smsto:15771234"));
                break;

            case R.id.guide_subway2:
                parseMessage(Uri.parse("smsto:1577-5678"));
                break;

            case R.id.guide_subway3:
                parseMessage(Uri.parse("smsto:1544-7769"));
                break;

            case R.id.guide_subway4:
                parseCall(Uri.parse("tel:02-2656-0009"));
                break;

            case R.id.guide_kids1:
                parseCall(Uri.parse("tel:112"));
                break;

            case R.id.guide_kids2:
                parseCall(Uri.parse("tel:182"));
                break;

            case R.id.guide_kids3:
                parseMessage(Uri.parse("smsto:#0182"));
                break;

            case R.id.guide_woman1:
                parseCall(Uri.parse("tel:02-338-5801"));
                break;

            case R.id.guide_woman2:
                parseCall(Uri.parse("tel:02-335-1858"));
                break;

            case R.id.guide_woman3:
                parseCall(Uri.parse("tel:1366"));
                break;

            case R.id.guide_woman4:
                parseCall(Uri.parse("tel:132"));
                break;

            case R.id.guide_woman5:
                parseCall(Uri.parse("tel:02-2263-6465"));
                break;

            case R.id.guide_woman6:
                parseCall(Uri.parse("tel:02-2269-0212"));
                break;

            case R.id.guide_taxi1:
                parseMessage(Uri.parse("smsto:"+editText.getText().toString()+"-120"));
                break;
        }
    }

    public void parseMessage(Uri number){
        startActivity(new Intent(Intent.ACTION_SENDTO, number));
    }

    public void parseCall(Uri number){
        startActivity(new Intent(Intent.ACTION_CALL, number));
    }
}
