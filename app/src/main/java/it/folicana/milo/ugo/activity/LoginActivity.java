package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;
import it.folicana.milo.ugo.model.UserModel;


public class LoginActivity extends Activity {

    private static final String TAG_LOG = LoginActivity.class.getName();
    public static final String LOGIN_ACTION = Const.PKG + ".action.LOGIN_ACTION";
    public static final String USER_DATA_EXTRA = Const.PKG + ".extra.USER_DATA_EXTRA";

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = (EditText) findViewById(R.id.username_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);

        mErrorTextView = (TextView) findViewById(R.id.error_message_label);

    }

    public void doLogin(View loginButton){
        this.mErrorTextView.setVisibility(View.INVISIBLE);
        final Editable usernameEdit = mUsernameEditText.getText();

        if(TextUtils.isEmpty(usernameEdit)){
            final String usernamedMandatory = getResources().getString(R.string.mandatory_field_error,"username");
            this.mErrorTextView.setText(usernamedMandatory);
            this.mErrorTextView.setVisibility(View.VISIBLE);
            return;

        }

        final Editable passwordEdit = mPasswordEditText.getText();

        if(TextUtils.isEmpty(passwordEdit)){
            final String passwordMandatory = getResources().getString(R.string.mandatory_field_error,"password");
            this.mErrorTextView.setText(passwordMandatory);
            this.mErrorTextView.setVisibility(View.VISIBLE);
            return;

        }

        final String username = usernameEdit.toString();
        final String password = passwordEdit.toString();

        final UserModel userModel = LoginService.get().login(username,password);

        if (userModel != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(USER_DATA_EXTRA, userModel);
            setResult(RESULT_OK, resultIntent);
            finish();

        }else{
            this.mErrorTextView.setText(R.string.wrong_credential_error);
            this.mErrorTextView.setVisibility(View.VISIBLE);
        }
    }
}
