package safeme.com.naver.cafe.safeme.siren;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import safeme.com.naver.cafe.safeme.R;

public class MySirenActivity extends AppCompatActivity {
    MediaPlayer mp = null;
    static int ON = 1;
    static int OFF = 0;
    int state = OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siren);


        final ImageButton playButton = (ImageButton) findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == ON) {
                    stopResAudio();
                    state = OFF;
                } else {
                    startResAudio();
                    state = ON;
                }
            }
        });
    }

    public void startResAudio(){
        mp = MediaPlayer.create(this, R.raw.policesiren);
        mp.start();
    }

    public void stopResAudio() {
        if (mp != null) {
            mp.stop();
            mp.release();
        }
        mp = null;
    }
    @Override
    protected  void onStop(){
        super.onStop();
        stopResAudio();
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
}