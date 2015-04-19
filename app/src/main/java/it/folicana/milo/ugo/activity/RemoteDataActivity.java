package it.folicana.milo.ugo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.fragment.RemoteDataFragment;

public class RemoteDataActivity extends FragmentActivity {


    private static final String TAG_LOG = RemoteDataActivity.class.getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        if (savedInstanceState == null) {
            final RemoteDataFragment fragment = new RemoteDataFragment();
            getFragmentManager().beginTransaction().add(R.id.anchor_point, fragment).commit();
        }
    }


}