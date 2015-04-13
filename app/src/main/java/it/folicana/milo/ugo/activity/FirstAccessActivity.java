package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.os.Bundle;

import it.folicana.milo.ugo.R;


public class FirstAccessActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}