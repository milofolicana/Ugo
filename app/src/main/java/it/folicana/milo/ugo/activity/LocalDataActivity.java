package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.fragment.LocalDataFragment;



public class LocalDataActivity extends FragmentActivity {

    final String TAG_LOG = LocalDataActivity.class.getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        if (savedInstanceState == null) {
            final LocalDataFragment fragment = new LocalDataFragment();
            getFragmentManager().beginTransaction().add(R.id.anchor_point, fragment).commit();




        }
    }


}


