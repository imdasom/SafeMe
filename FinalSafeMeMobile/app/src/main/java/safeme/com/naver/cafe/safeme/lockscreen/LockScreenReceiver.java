package safeme.com.naver.cafe.safeme.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import safeme.com.naver.cafe.safeme.MainActivity;

public class LockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        //If the screen was just turned on or it just booted up, start your Lock Activity
        if(action.equals(Intent.ACTION_SCREEN_ON) || action.equals(Intent.ACTION_BOOT_COMPLETED))
        {
            Intent i = new Intent(context, LockScreenActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}