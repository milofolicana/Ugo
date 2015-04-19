package it.folicana.milo.ugo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.folicana.milo.ugo.R;

public class NewDataFragment extends Fragment {


    private static final String TAG_LOG = NewDataFragment.class.getName();


    public interface NewDataFragmentListener {

       void newDataForCategory(String category);

    }


    private NewDataFragmentListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof NewDataFragmentListener) {
            mListener = (NewDataFragmentListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View menuView = inflater.inflate(R.layout.fragment_new_data, null);

        menuView.findViewById(R.id.new_love_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d(TAG_LOG, "Data for Love!");
                    mListener.newDataForCategory(getResources().getString(R.string.love_label));
                }
            }
        });
        // Health
        menuView.findViewById(R.id.new_health_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d(TAG_LOG, "Data for Health!");
                    mListener.newDataForCategory(getResources().getString(R.string.health_label));
                }
            }
        });

        menuView.findViewById(R.id.new_work_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d(TAG_LOG, "Data for Work!");
                    mListener.newDataForCategory(getResources().getString(R.string.work_label));
                }
            }
        });

        menuView.findViewById(R.id.new_luck_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d(TAG_LOG, "Data for Luck!");
                    mListener.newDataForCategory(getResources().getString(R.string.luck_label));
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
