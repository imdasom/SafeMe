package safeme.com.naver.cafe.safeme.lockscreen;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import safeme.com.naver.cafe.safeme.MainActivity;

public class LockScreenService extends Service {

    BroadcastReceiver receiver;
    private MainActivity mContext;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void setContext(MainActivity mContext){
        this.mContext = mContext;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate() {
        KeyguardManager.KeyguardLock key;
        KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);

        //This is deprecated, but it is a simple way to disable the lockscreen in code
        key = km.newKeyguardLock("IN");

        key.disableKeyguard();

        //Start listening for the Screen On, Screen Off, and Boot completed actions
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);

        //Set up a receiver to listen for the Intents in this Service
        receiver = new LockScreenReceiver();
        registerReceiver(receiver, filter);
        Log.d("safeme", "리스닝 서비스 시작 onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("safeme", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        Log.d("safeme", "unregisterReceiver");
        super.onDestroy();
    }
}
