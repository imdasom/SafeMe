package safeme.com.naver.cafe.safeme.map.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by dasom on 2017-05-21.
 */
public class DialogFactory {
    public static ProgressDialog getWaitingDialog(Context context) {
        ProgressDialog dialog = ProgressDialog.show(context, "위치 설정중", "잠시만 기다려주세요.", true);
        dialog.setCancelable(false);
        return dialog;
    }
}
