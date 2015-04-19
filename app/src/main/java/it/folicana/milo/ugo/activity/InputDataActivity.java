package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;
import it.folicana.milo.ugo.fragment.InputDataFragment;

public class InputDataActivity extends FragmentActivity implements InputDataFragment.InputDataFragmentListener {


    private static final String TAG_LOG = InputDataActivity.class.getName();
    public static final String INPUT_TYPE_EXTRA = Const.PKG + ".extra.INPUT_TYPE_EXTRA";

    public static final String INPUT_VALUE_EXTRA = Const.PKG + ".extra.INPUT_VALUE_EXTRA";


    private TextView mQuestionTextView;
    private String mQuestionType;

    private boolean mIsDouble;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mIsDouble = findViewById(R.id.right_anchor) != null;
        if (mIsDouble) {

            finish();
        }

        mQuestionType = getIntent().getStringExtra(INPUT_TYPE_EXTRA);
        if (TextUtils.isEmpty(mQuestionType)) {
            Log.w(TAG_LOG, "The value for the extra " + INPUT_TYPE_EXTRA + " is mandatory");
            finish();
        }

        if (savedInstanceState == null) {
            final InputDataFragment fragment = InputDataFragment.getInputDataFragment(mQuestionType);
            getFragmentManager().beginTransaction().add(R.id.anchor_point, fragment).commit();
        }

    }

    @Override
    public void valueForCategory(String category, long vote) {
        // We create the Intent to return
        final Intent returnIntent = new Intent();
        returnIntent.putExtra(INPUT_TYPE_EXTRA, mQuestionType);
        returnIntent.putExtra(INPUT_VALUE_EXTRA, category);
        // So far we just return
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}