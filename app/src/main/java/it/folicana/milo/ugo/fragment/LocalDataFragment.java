package it.folicana.milo.ugo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.folicana.milo.ugo.R;


public class LocalDataFragment extends Fragment {


    private static final String TAG_LOG = LocalDataFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // We create the layout for this fragment
        final View localDataView = inflater.inflate(R.layout.fragment_local_data, null);
        // We return the View for the fragment
        return localDataView;
    }

}

