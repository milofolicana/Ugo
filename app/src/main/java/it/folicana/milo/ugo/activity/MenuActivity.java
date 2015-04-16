package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;
import it.folicana.milo.ugo.model.UserModel;


public class MenuActivity extends Activity {

    private static final String TAG_LOG = MenuActivity.class.getName();

    public static final String USER_EXTRA = Const.PKG + ".extra.USER_EXTRA";

    private UserModel mUserModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.mUserModel = (UserModel) getIntent().getParcelableExtra(USER_EXTRA);
        if (mUserModel == null) {
            Log.w(TAG_LOG, USER_EXTRA + "is mandatory!");
            finish();
        }

    }
}
