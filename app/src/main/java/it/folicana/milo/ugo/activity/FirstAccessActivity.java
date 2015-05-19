package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.fragment.FirstAccessFragment;
import it.folicana.milo.ugo.model.UserModel;


public class FirstAccessActivity extends FragmentActivity implements FirstAccessFragment.FirstAccessListener {

    private static final String TAG_LOG = SplashActivity.class.getName();
    private static final int LOGIN_REQUEST_ID = 1;
    private static final int REGISTRATION_REQUEST_ID = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        if (savedInstanceState == null){
            final FirstAccessFragment fragment = new FirstAccessFragment();
            getFragmentManager().beginTransaction().add(R.id.anchor_point,fragment).commit();
        }
/*
        final Button anonymousButton = (Button) findViewById(R.id.anonymous_button);
        anonymousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAsAnonymous();
            }
        });

        final Button registrationButton = (Button) findViewById(R.id.register_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegistration();
            }
        });

        final Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });   */
    }

    public void enterAsAnonymous() {
        Log.d(TAG_LOG, "Anonymous access");
        final Intent anonymousIntent = new Intent(this,MenuActivity.class);
        final UserModel userModel = UserModel.create(System.currentTimeMillis());
        anonymousIntent.putExtra(MenuActivity.USER_EXTRA, userModel);
        startActivity(anonymousIntent);

    }

    public void doRegistration() {
        final Intent registrationIntent = new Intent(RegisterActivity.REGISTRATION_ACTION);
        startActivityForResult(registrationIntent, REGISTRATION_REQUEST_ID);
    }

    public void doLogin() {
        final Intent loginIntent = new Intent(LoginActivity.LOGIN_ACTION);
        startActivityForResult(loginIntent, LOGIN_REQUEST_ID);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == LOGIN_REQUEST_ID){
            switch (resultCode){
                case RESULT_OK:
                    final UserModel userModel = (UserModel)data.getParcelableExtra(LoginActivity.USER_DATA_EXTRA);
                    final Intent mainIntent = new Intent(this, MenuActivity.class);
                    mainIntent.putExtra(MenuActivity.USER_EXTRA, userModel);
                    startActivity(mainIntent);
                    finish();
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }else if (requestCode == REGISTRATION_REQUEST_ID){
            switch (resultCode){
                case RESULT_OK:
                    final UserModel userModel = (UserModel) data.getParcelableExtra(RegisterActivity.USER_DATA_EXTRA);
                    final Intent detailIntent = new Intent (ShowUserDataActivity.SHOW_USER_ACTION);
                    detailIntent.putExtra(ShowUserDataActivity.USER_EXTRA, userModel);
                    startActivity(detailIntent);
                    break;
                case RESULT_CANCELED:
                    break;


            }
        }
    }
}