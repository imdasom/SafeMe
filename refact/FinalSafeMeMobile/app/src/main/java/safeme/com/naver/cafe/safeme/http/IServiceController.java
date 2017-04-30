package safeme.com.naver.cafe.safeme.http;

import android.os.Handler;

/**
 * Created by dasom on 2017-04-30.
 */
public interface IServiceController {
    void setHandler(Handler handler);
    void onPreExecute(Object... objects);   //파라미터를 Object...으로 하는 것은 고민해봐야 할 듯...
    void onPostExecute(Object... objects);
}
