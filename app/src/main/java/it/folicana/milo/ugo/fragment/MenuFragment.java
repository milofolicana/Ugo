package it.folicana.milo.ugo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.folicana.milo.ugo.R;

public class MenuFragment extends Fragment {


    private static final String TAG_LOG = MenuFragment.class.getName();


    //Interfaccia che dovrà essere implementata dall'activity che utilizza questo fragment

    public interface MenuFragmentListener {


        void insertNewData();

        void viewOldData();

        void viewRemoteData();

    }

    //referenzia il listener di questo fragment
    private MenuFragmentListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MenuFragmentListener) {
            mListener = (MenuFragmentListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View menuView = inflater.inflate(R.layout.fragment_menu, null);

        menuView.findViewById(R.id.insert_new_data_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d(TAG_LOG, "User requests to insert a new data!");
                    mListener.insertNewData();
                }
            }
        });

        menuView.findViewById(R.id.view_old_data_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d(TAG_LOG, "User requests to view local data!");
                    mListener.viewOldData();
                }
            }
        });

        menuView.findViewById(R.id.view_remote_data_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d(TAG_LOG, "User requests to view remote data!");
                    mListener.viewRemoteData();
                }
            }
        });

        return menuView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
