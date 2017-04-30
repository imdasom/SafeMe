package safeme.com.naver.cafe.safeme.lockscreen;

import android.app.ActionBar;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import net.majorkernelpanic.streaming.rtsp.UriParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.jar.Manifest;

import safeme.com.naver.cafe.safeme.MainActivity;
import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.lockscreen.LockScreenService;
import safeme.com.naver.cafe.safeme.video.MyVideoActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by dasom on 2015-12-12.
 */
public class LockScreenActivity extends Activity{

    private static final String IMAGEVIEW_TAG = "icon bitmap";
    private MainActivity mContext;
    private static ClipData dragData;
    private ImageView button1, entered;
    private Drawable normalShape;
    private Drawable targetShape;
    private TextClock textClock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up our Lockscreen
        makeFullScreen();

        setContentView(R.layout.activity_lock);

        textClock = (TextClock)findViewById(R.id.textClock);

        button1 = (ImageView) findViewById(R.id.button1);
        button1.setTag(IMAGEVIEW_TAG);

        entered = (ImageView)findViewById(R.id.entered);

        // Sets a long click listener for the ImageView using an anonymous listener object that
        // implements the OnLongClickListener interface
        button1.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                // Create a new ClipData.
                // This is done in two steps to provide clarity. The convenience method
                // ClipData.newPlainText() can create a plain text ClipData in one step.

                // Create a new ClipData.Item from the ImageView object's tag
                ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());

                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This will create a new ClipDescription object within the
                // ClipData, and set its MIME type entry to "text/plain"
                dragData = new ClipData(v.getTag().toString(), mimeTypes, item);

                // Instantiates the drag shadow builder.
                View.DragShadowBuilder myShadow = new MyDragShadowBuilder(v);

                // Starts the drag

                v.startDrag(dragData,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        null,      // no need to use local data
                        0          // flags (not currently used, set to 0)
                );

                return false;
            }
        });


        // Creates a new drag event listener
        myDragEventListener mDragListen = new myDragEventListener();
        button1.setOnDragListener(mDragListen);

        normalShape = getResources().getDrawable(
                R.drawable.lockscreen_button);
        targetShape = getResources().getDrawable(
                R.drawable.lockscreen_button_pressed);
        button1.setBackground(normalShape);

        findViewById(R.id.linearLayout_notify).setOnDragListener(new myDragEventListener());
        findViewById(R.id.linearLayout_exit).setOnDragListener(new myDragEventListener());
    }

    public void setContext(MainActivity mContext){
        this.mContext = mContext;
    }

    @Override
    protected void onResume() {
        /*Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        ImageView background = (ImageView)findViewById(R.id.backgroundImage);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable backImage = wallpaperManager.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)backImage).getBitmap();
        Bitmap bitmap2 = bitmap;
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap2);
        canvas.restore();
        backImage.draw(canvas);
        background.setImageDrawable(backImage);
        //wallpaperManager.getBuiltInDrawable();
        //Rect mRect = backImage.getBounds();
        TextView textView = (TextView)findViewById(R.id.backgroundText);
        textView.setBackgroundColor(Color.BLACK);
        textView.setTextColor(Color.WHITE);
        *//*textView.setText("backImage의 mRect.상하좌우 : "+mRect.top+", "+mRect.bottom+", "+mRect.left+", "+mRect.right+"\n"
                +"mRect.centerXY() : "+mRect.centerX()+","+mRect.centerY()+"\n"
                +"mRect.width, height() : "+mRect.width()+", "+mRect.height()+"\n"
                +"backImage.getIntrinsicWidth, height() : "+backImage.getIntrinsicWidth()+", "+backImage.getIntrinsicHeight());*//*
        textView.setText("\nbackImage.getIntrinsicWidth, height() : " + backImage.getIntrinsicWidth() + ", " + backImage.getIntrinsicHeight());
        //wallpaperManager.suggestDesiredDimensions(1080, 1920);*/

        //backImage = wallpaperManager.getBuiltInDrawable(400, 450, false, 0.0f, 0.0f);
        //backImage.setBounds(0, 0, 300, 450);
        //textView.append("\nbackImage.getIntrinsicWidth, height() : " + backImage.getIntrinsicWidth() + ", " + backImage.getIntrinsicHeight());
        //Bitmap bitmap = Bitmap.createBitmap(backImage.getIntrinsicWidth(), backImage.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        //backImage.setBounds(0, 0, display.getWidth(), display.getHeight());
        //Bitmap bitmap = ((BitmapDrawable)backImage).getBitmap();
        //background.setImageBitmap(bitmap);
        //Canvas canvas = new Canvas();
        //canvas.setBitmap(bitmap);
        //background.setClipBounds(new Rect(0, 0, display.getWidth(), display.getHeight()));
        //background.draw(canvas);

        //background.setDrawingCacheEnabled(true);
        //background.setImageDrawable(backImage);
        //Bitmap bitmap = Bitmap.createBitmap(backImage.getIntrinsicWidth(), backImage.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        //background.setImageBitmap(bitmap);
        //background.setImageDrawable(backImage);
        super.onResume();

        /*try {

            TextView textView = (TextView)findViewById(R.id.backgroundText);
            textView.setBackgroundColor(Color.BLACK);
            textView.setTextColor(Color.WHITE);

            Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
            LinearLayout background = (LinearLayout)findViewById(R.id.backgroundImage);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            File file  = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/safeme/lockImage.jpeg");

            Drawable backImage = wallpaperManager.getDrawable();
            int width = 100;
            int height = 200;
            //backImage = wallpaperManager.getDrawable().getCurrent();
            //backImage = wallpaperManager.getBuiltInDrawable(0, 0, false, 1.0f, 1.0f);
            Bitmap bitmap = ((BitmapDrawable)backImage).getBitmap();
            //bitmap.createScaledBitmap(bitmap, display.getWidth(), display.getHeight(), true);

            //File newFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Launcher/a7c8ff019191a000.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            FileInputStream fileInputStream = new FileInputStream(file);
            //bitmap = BitmapFactory.decodeStream(fileInputStream);
            //background.setImageBitmap(bitmap);
            //background.setBackground(Drawable.createFromStream(fileInputStream, "a7c8ff019191a000.jpg"));
            //background.setBackground(Drawable.createFromStream(fileInputStream, "lockImage.jpeg"));
            //background.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            //background.setBackground(getResources().getDrawable(R.drawable.background));
            //wallpaperManager.getBuiltInDrawable();
            //Rect mRect = backImage.getBounds();

        *//*textView.setText("backImage의 mRect.상하좌우 : "+mRect.top+", "+mRect.bottom+", "+mRect.left+", "+mRect.right+"\n"
                +"mRect.centerXY() : "+mRect.centerX()+","+mRect.centerY()+"\n"
                +"mRect.width, height() : "+mRect.width()+", "+mRect.height()+"\n"
                +"backImage.getIntrinsicWidth, height() : "+backImage.getIntrinsicWidth()+", "+backImage.getIntrinsicHeight());*//*
            //textView.setText("\nbackImage.getIntrinsicWidth, height() : " + backImage.getIntrinsicWidth() + ", " + backImage.getIntrinsicHeight());
            //wallpaperManager.suggestDesiredDimensions(1080, 1920);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    /**
     * A simple method that sets the screen to fullscreen.  It removes the Notifications bar,
     * the Actionbar and the virtual keys (if they are on the phone)
     */
    public void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON //(부모뷰).bringtoChild(자식뷰)
        );
        if (Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        return; //Do nothing!
    }

    public void unlockScreen(View view) {
        //Instead of using finish(), this totally destroys the process
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    // developer 사이트에서...

    private class myDragEventListener implements View.OnDragListener {

        // This is the method that the system calls when it dispatches a drag event to the
        // listener.
        public boolean onDrag(View v, DragEvent event) {

            // Defines a variable to store the action type for the incoming event
            final int action = event.getAction();

            // Handles each of the expected events
            switch (action) {

                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                    button1.setBackground(targetShape);
                    button1.setVisibility(View.GONE);

                    LinearLayout linearLayout3 = (LinearLayout)findViewById(R.id.linearLayout_notify);
                    linearLayout3.setBackgroundResource(R.drawable.lockscreen_notify_entered);

                    LinearLayout linearLayout4 = (LinearLayout)findViewById(R.id.linearLayout_exit);
                    linearLayout4.setBackgroundResource(R.drawable.lockscreen_exit_entered);

                    ImageView entered_background = (ImageView)findViewById(R.id.enterd_backgournd);
                    entered_background.setBackgroundResource(R.drawable.lockscreen_entered_background);



                    // Determines if this View can accept the dragged data
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                        // As an example of what your application might do,
                        // applies a blue color tint to the View to indicate that it can accept
                        // data.
                        //v.setBackgroundColor(Color.BLUE);
                        //button1.setBackgroundResource(R.drawable.lockscreen_button);

                        // Invalidate the view to force a redraw in the new tint
                        button1.invalidate();

                        // returns true to indicate that the View can accept the dragged data.
                        return true;

                    }

                    // Returns false. During the current drag and drop operation, this View will
                    // not receive events again until ACTION_DRAG_ENDED is sent.
                    return false;

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENTERED");

                    if (v == findViewById(R.id.linearLayout_notify)) {
                        //button1.setBackground(targetShape);
                        //entered.setBackgroundResource(R.drawable.lockscreen_notify);
                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout_notify);
                        linearLayout.setBackgroundResource(R.drawable.lockscreen_notify_entered_red);


                    }else if (v == findViewById(R.id.linearLayout_exit)) {
                        //button1.setBackground(targetShape);
                        //entered.setBackgroundResource(R.drawable.lockscreen_exit);
                        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout_exit);
                        linearLayout.setBackgroundResource(R.drawable.lockscreen_exit_entered_red);
                    } else {
                    }

                    // Invalidate the view to force a redraw in the new tint
                    button1.invalidate();

                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:

                    // Ignore the event
                    return true;

                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");

                    //button1.setBackground(normalShape);
                    entered.setBackgroundColor(Color.TRANSPARENT);

                    LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout_notify);
                    linearLayout1.setBackgroundResource(R.drawable.lockscreen_notify_entered);

                    LinearLayout linearLayout2 = (LinearLayout)findViewById(R.id.linearLayout_exit);
                    linearLayout2.setBackgroundResource(R.drawable.lockscreen_exit_entered);
                    // Invalidate the view to force a redraw in the new tint
                    button1.invalidate();

                    return true;

                case DragEvent.ACTION_DROP:
                    Log.d("DragClickListener", "ACTION_DROP");

                    // Gets the item containing the dragged data
                    //ClipData.Item item = event.getClipData().getItemAt(0);

                    // Gets the text data from the item.
                    //dragData = item.getText();

                    // Displays a message containing the dragged data.
                    //Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_LONG);

                    // Turns off any color tints
                    //v.setBackgroundResource(R.drawable.lockscreen_button);

                    // Invalidates the view to force a redraw
                    //v.invalidate();

                    if (v == findViewById(R.id.linearLayout_notify)) {
                        //View view = (View) event.getLocalState();
                        //ViewGroup viewgroup = (ViewGroup) view.getParent();
                        //viewgroup.removeView(view);

                        //v.setBackgroundResource(R.drawable.lockscreen_button_pressed);

                        SharedPreferences sharedPreferences = getSharedPreferences("DIRECT_TO_VIDEO", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("directToVideo", true);
                        editor.commit();

                        Intent intent = new Intent(LockScreenActivity.this, MainActivity.class);
                        intent.putExtra("directToNofity", true);


                        startActivity(intent);
                        finish();
                        //unlockScreen(button1);
                        //Toast.makeText(getApplicationContext(), "신고하기.", Toast.LENGTH_SHORT).show();

                    }else if (v == findViewById(R.id.linearLayout_exit)) {
                        //View view = (View) event.getLocalState();
                        //ViewGroup viewgroup = (ViewGroup) view.getParent();
                        //viewgroup.removeView(view);

                        //v.setBackgroundResource(R.drawable.lockscreen_button_pressed);
                        //unlockScreen(v);
                        //finish();
                        //Intent intent = new Intent(this, MainActivity.class);
                        //intent.putExtra("directToNofity", true);
                        //Intent intent = new Intent(this, MyVideoActivity.class);
                        //startActivity(intent);
                        //finish();
                        unlockScreen(button1);
                        Toast.makeText(getApplicationContext(), "나가기.", Toast.LENGTH_SHORT).show();

                    }else {
                        break;
                    }

                    // Returns true. DragEvent.getResult() will return true.
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");
                    button1.setBackgroundResource(R.drawable.lockscreen_button);
                    button1.setVisibility(View.VISIBLE);

                    LinearLayout linearLayout5 = (LinearLayout)findViewById(R.id.linearLayout_notify);
                    linearLayout5.setBackgroundResource(R.drawable.lockscreen_notify);

                    LinearLayout linearLayout6 = (LinearLayout)findViewById(R.id.linearLayout_exit);
                    linearLayout6.setBackgroundResource(R.drawable.lockscreen_exit);

                    ImageView entered_background2 = (ImageView)findViewById(R.id.enterd_backgournd);
                    entered_background2.setBackgroundColor(Color.TRANSPARENT);

                    // Turns off any color tinting
                    //v.setBackgroundColor(Color.RED);

                    //button1.setBackground(normalShape);

                    // Invalidates the view to force a redraw
                    button1.invalidate();

                    // Does a getResult(), and displays what happened.
                    if (event.getResult()) {
                        //Toast.makeText(LockScreenActivity.this, "The drop was handled.", Toast.LENGTH_LONG);

                    } else {
                        //Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG);

                    }

                    // returns true; the value is ignored.
                    return true;

                // An unknown action type was received.
                default:
                    Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                    break;
            }

            return false;
        }
    }

    private class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private Drawable shadow;

        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);

            // Creates a draggable image that will fill the Canvas provided by the system.
            //shadow = new ColorDrawable(Color.LTGRAY);
            shadow = getResources().getDrawable(R.drawable.lockscreen_button_pressed);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            // Defines local variables
            int width, height;

            // Sets the width of the shadow to half the width of the original View
            //width = getView().getWidth() / 2;
            width = getView().getWidth();

            // Sets the height of the shadow to half the height of the original View
            //height = getView().getHeight() / 2;
            height = getView().getHeight();

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, width, height);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height);

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(width / 2, height / 2);
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {

            // Draws the ColorDrawable in the Canvas passed in from the system.
            shadow.draw(canvas);
        }
    }


}


