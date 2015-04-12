package it.folicana.milo.ugo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Menu;



public class SplashActivity extends Activity {

    private static final long MIN_WAIT_INTERVAL = 1500L;
    private static final long MAX_WAIT_INTERVAL =  3000L;
    private static final int GO_AHEAD_WHAT = 1;

    private long mStartTime = -1L;
    private boolean mIsDone;

    private Handler mHandler = new Handler() {

        @Override //classe anonima, metodo necessario
                  //per la ricezione dei messaggi
        public void handleMessage(Message msg){
            switch(msg.what) {
                case GO_AHEAD_WHAT:
                    long elapsedTime = SystemClock.uptimeMillis() - mStartTime;
                    if (elapsedTime >= MIN_WAIT_INTERVAL && !mIsDone){
                        mIsDone = true;
                        goAhead();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStartTime = SystemClock.uptimeMillis();
        final Message goAheadMessage = mHandler.obtainMessage(GO_AHEAD_WHAT);
        mHandler.sendMessageAtTime(goAheadMessage,mStartTime + MAX_WAIT_INTERVAL);
    }


    private void goAhead() {
        final Intent intent = new Intent(this, FirstAccesActivity.class);
        startActivity(intent);
        finish();
    }

}
