package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;
import it.folicana.milo.ugo.model.UserModel;

/**
 * Created by Milo on 17/04/2015.
 */
public class NewDataActivity extends Activity {

    private static final String TAG_LOG = NewDataActivity.class.getName();

    public static final String USER_EXTRA = Const.PKG + ".extra.USER_EXTRA";

    /**
     * Id for the input data request
     */
    private static final int INPUT_DATA_REQUEST_ID = 1;

    /**
     * The current UserModel
     */
    private UserModel mUserModel;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data);
        // We get the UserModel
        this.mUserModel = (UserModel) getIntent().getParcelableExtra(USER_EXTRA);
        if (mUserModel == null) {
            Log.w(TAG_LOG, USER_EXTRA + " is mandatory!");
            finish();
        }
    }

    /**
     * This is invoked when we press the newDataButton
     *
     * @param newDataButton The Button pressed
     */
    public void insertNewData(View newDataButton) {
        final Intent questionIntent = new Intent(this, InputDataActivity.class);
        String questionType = null;
        switch (newDataButton.getId()) {
            case R.id.new_love_data:
                questionType = getResources().getString(R.string.love_label);
                break;
            case R.id.new_health_data:
                questionType = getResources().getString(R.string.health_label);
                break;
            case R.id.new_work_data:
                questionType = getResources().getString(R.string.work_label);
                break;
            case R.id.new_luck_data:
                questionType = getResources().getString(R.string.luck_label);
                break;
        }
        questionIntent.putExtra(InputDataActivity.INPUT_TYPE_EXTRA, questionType);
        startActivityForResult(questionIntent, INPUT_DATA_REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INPUT_DATA_REQUEST_ID && resultCode == RESULT_OK) {
            // Here we'll manage the data from the questions
            // TODO Manage data from the questions

        }
    }
}



