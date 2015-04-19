package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;
import it.folicana.milo.ugo.fragment.MenuFragment;
import it.folicana.milo.ugo.model.UserModel;


public class MenuActivity extends FragmentActivity implements MenuFragment.MenuFragmentListener {

    private static final String TAG_LOG = MenuActivity.class.getName();
    public static final String USER_EXTRA = Const.PKG + ".extra.USER_EXTRA";
    private static final String MENU_FRAGMENT_TAG = Const.PKG + ".tag.MENU_FRAGMENT_TAG";

   private UserModel mUserModel;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        // We get the UserModel
        this.mUserModel = (UserModel) getIntent().getParcelableExtra(USER_EXTRA);
        if (mUserModel == null) {
            Log.w(TAG_LOG, USER_EXTRA + " is mandatory!");
            finish();
        }

        if (savedInstanceState == null) {
            final MenuFragment fragment = new MenuFragment();
            getFragmentManager().beginTransaction().add(R.id.anchor_point, fragment, MENU_FRAGMENT_TAG).commit();
        }
    }


    @Override
    public void insertNewData() {
        Log.d(TAG_LOG, "We choose to insert new data");
        final Intent newDataIntent = new Intent(this, NewDataActivity.class);
        newDataIntent.putExtra(NewDataActivity.USER_EXTRA, mUserModel);
        startActivity(newDataIntent);
    }


    @Override
    public void viewOldData() {
        Log.d(TAG_LOG, "We choose to view our old data");
        final Intent localDataIntent = new Intent(this, LocalDataActivity.class);
        startActivity(localDataIntent);
    }


    @Override
    public void viewRemoteData() {
        Log.d(TAG_LOG, "We choose to view remote data");
        final Intent remoteDataIntent = new Intent(this, RemoteDataActivity.class);
        startActivity(remoteDataIntent);
    }



}
