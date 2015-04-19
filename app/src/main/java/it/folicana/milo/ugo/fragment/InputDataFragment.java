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
import it.folicana.milo.ugo.conf.Const;

public class InputDataFragment extends Fragment {


    private static final String TAG_LOG = InputDataFragment.class.getName();
    private static final String CATEGORY_ARG_KEY = Const.PKG + ".key.CATEGORY_ARG_KEY";



    public static InputDataFragment getInputDataFragment(final String category) {
        // We create the InputDataFragment saving the category as arguments
        final InputDataFragment fragment = new InputDataFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_ARG_KEY, category);
        fragment.setArguments(args);
        return fragment;
    }


    public interface InputDataFragmentListener {


        void valueForCategory(String category, long vote);

    }


    private InputDataFragmentListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof InputDataFragmentListener) {
            mListener = (InputDataFragmentListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final String category = getArguments().getString(CATEGORY_ARG_KEY);

        final View inputDataView = inflater.inflate(R.layout.fragment_input_data, null);
        // We note that we don't care about the Button type because the onClick is an event
        // of all the View
        inputDataView.findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d(TAG_LOG, "Send value for category " + category);
                    // So far we return always the same value. TODO Make it dynamic
                    mListener.valueForCategory(category, 10);
                }
            }
        });
        // We set the question
        final TextView questionTextView = (TextView) inputDataView.findViewById(R.id.data_question);
        final String question = getResources().getString(R.string.data_question_format, category);
        questionTextView.setText(question);
        // We return the View for the fragment
        return inputDataView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}

