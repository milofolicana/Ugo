package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;
import it.folicana.milo.ugo.fragment.InputDataFragment;
import it.folicana.milo.ugo.fragment.NewDataFragment;
import it.folicana.milo.ugo.model.UserModel;

/**
 * Created by Milo on 17/04/2015.
 */
public class NewDataActivity extends FragmentActivity implements NewDataFragment.NewDataFragmentListener {

    private static final String TAG_LOG = NewDataActivity.class.getName();
    public static final String USER_EXTRA = Const.PKG + ".extra.USER_EXTRA";
    public static final String CURRENT_CATEGORY_KEY = Const.PKG + ".key.CURRENT_CATEGORY_KEY";
    private static final int INPUT_DATA_REQUEST_ID = 1;


    private UserModel mUserModel;

    private boolean mIsDouble;
    private String mCurrentCategory;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mIsDouble = findViewById(R.id.right_anchor) != null;

        this.mUserModel = (UserModel) getIntent().getParcelableExtra(USER_EXTRA);
        if (mUserModel == null) {
            Log.w(TAG_LOG, USER_EXTRA + " is mandatory!");
            finish();
        }

        if (savedInstanceState == null) {
            final NewDataFragment fragment = new NewDataFragment();
            getFragmentManager().beginTransaction().add(R.id.anchor_point, fragment).commit();

            final String defaultCategory = getResources().getString(R.string.love_label);
            mCurrentCategory = defaultCategory;
        } else {
            mCurrentCategory = savedInstanceState.getString(CURRENT_CATEGORY_KEY);
        }
        // If the screen is double we have to check if anything is already present. If not
        // we insert the first
        if (mIsDouble) {
            Fragment rightFragment = InputDataFragment.getInputDataFragment(mCurrentCategory);
            getFragmentManager().beginTransaction().replace(R.id.right_anchor, rightFragment).commit();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // We save the current category
        outState.putString(CURRENT_CATEGORY_KEY, mCurrentCategory);
    }


    @Override
    public void newDataForCategory(final String category) {
        // We change the fragment on the right only if different
        if (mIsDouble && !category.equals(mCurrentCategory)) {
            // In this case we have to replace the fragment on the right
            InputDataFragment newFragment = InputDataFragment.getInputDataFragment(category);
            // We replace the Fragment on the right
            getFragmentManager().beginTransaction().replace(R.id.right_anchor, newFragment).commit();
            // We update the current category
            mCurrentCategory = category;
        } else {
            // We update the current category
            mCurrentCategory = category;
            final Intent questionIntent = new Intent(this, InputDataActivity.class);
            questionIntent.putExtra(InputDataActivity.INPUT_TYPE_EXTRA, category);
            startActivityForResult(questionIntent, INPUT_DATA_REQUEST_ID);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INPUT_DATA_REQUEST_ID && resultCode == RESULT_OK) {
            // Here we'll manage the data from the questions
            // TODO Manage data from the questions

        }
    }
}



