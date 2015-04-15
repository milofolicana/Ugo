package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import it.folicana.milo.ugo.R;


public class FirstAccessActivity extends Activity {

    private static final String TAG_LOG = SplashActivity.class.getName();
    private static final int LOGIN_REQUEST_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        });
    }

    private void enterAsAnonymous() {
        Log.d(TAG_LOG, "Anonymous access");
        final Intent anonymousIntent = new Intent(this,MenuActivity.class);
        startActivity(anonymousIntent);
    }

    private void doRegistration() {

    }

    private void doLogin() {
        final Intent loginIntent = new Intent(LoginActivity.LOGIN_ACTION);
        startActivityForResult(loginIntent, LOGIN_REQUEST_ID);
    }
}