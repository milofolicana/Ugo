package it.folicana.milo.ugo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.folicana.milo.ugo.R;

public class RemoteDataFragment extends Fragment {


    private static final String TAG_LOG = RemoteDataFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View remoteDataView = inflater.inflate(R.layout.fragment_remote_data, null);
        return remoteDataView;
    }

}