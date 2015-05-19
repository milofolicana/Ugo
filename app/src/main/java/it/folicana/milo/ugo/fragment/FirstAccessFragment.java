package it.folicana.milo.ugo.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.folicana.milo.ugo.R;


public class FirstAccessFragment extends Fragment {




    public interface FirstAccessListener {
        void enterAsAnonymous();
        void doLogin();
        void doRegistration();
    }

    private FirstAccessListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FirstAccessListener) {
            mListener = (FirstAccessListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View firstAccessView = inflater.inflate(R.layout.fragment_main, null);
        firstAccessView.findViewById(R.id.anonymous_button).setOnClickListener(new View.OnClickListener(){

            @Override
        public void onClick(View view){
                if (mListener != null) {
                    mListener.enterAsAnonymous();
                }
            }

        });

        firstAccessView.findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view) {
                if (mListener != null){
                    mListener.doLogin();
                }
            }
        });

        firstAccessView.findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view){
                if (mListener != null){
                    mListener.doRegistration();
                }
            }
        });

        return firstAccessView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

}

