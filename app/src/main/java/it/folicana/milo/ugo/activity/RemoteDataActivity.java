package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.os.Bundle;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;


public class RemoteDataActivity extends Activity {

    private static final String TAG_LOG = RemoteDataActivity.class.getName();
    public static final String INPUT_TYPE_EXTRA = Const.PKG + ".extra.INPUT_TYPE_EXTRA";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_data);
    }

}

