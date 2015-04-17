package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;

/**
 * Created by Milo on 17/04/2015.
 */
public class InputDataActivity extends Activity{

    /**
     * The Tag of the Log for this class
     */
    private static final String TAG_LOG = InputDataActivity.class.getName();

    /**
     * The name of the extra that will contain the type of question to do
     */
    public static final String INPUT_TYPE_EXTRA = Const.PKG + ".extra.INPUT_TYPE_EXTRA";

    /**
     * The type of the question to do
     */
    private String mQuestionType;

    /**
     * The TextView with the question
     */
    private TextView mQuestionTextView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        // We read the type of the question
        mQuestionType = getIntent().getStringExtra(INPUT_TYPE_EXTRA);
        if (TextUtils.isEmpty(mQuestionType)) {
            Log.w(TAG_LOG, "The value for the extra " + INPUT_TYPE_EXTRA + " is mandatory");
            finish();
        }
        // We get the reference to the TextView for the question
        mQuestionTextView = (TextView) findViewById(R.id.data_question);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // We set the question
        final String question = getResources().getString(R.string.data_question_format, mQuestionType);
        mQuestionTextView.setText(question);
    }

    /**
     * This is invoked when we press the sendDataButton
     *
     * @param sendDataButton The Button pressed
     */
    public void sendData(View sendDataButton) {
        // So far we just return
        setResult(RESULT_OK);
        finish();
    }


}
