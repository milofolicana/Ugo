package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;
import it.folicana.milo.ugo.model.UserModel;
import it.folicana.milo.ugo.service.RegistrationService;


public class RegisterActivity  extends Activity {
    private static final String TAG_LOG = RegisterActivity.class.getName();

    public static final String REGISTRATION_ACTION = Const.PKG + ".action.REGISTRATION_ACTION";
    public static final String USER_DATA_EXTRA = Const.PKG + ".extra.USER_DATA_EXTRA";

    private DatePicker mBirthDatePicker;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mEmailEditText;
    private EditText mLocationEditText;
    private TextView mErrorTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUsernameEditText = (EditText) findViewById(R.id.username_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);
        mEmailEditText = (EditText) findViewById(R.id.email_edittext);
        mBirthDatePicker = (DatePicker) findViewById(R.id.birth_date_datepicker);
        mLocationEditText = (EditText) findViewById(R.id.location_edittext);
        mErrorTextView = (TextView) findViewById(R.id.error_message_label);

    }

    public void doRegistration(View regButton) {
        Log.d(TAG_LOG, "doLogin!");
        // We hide the error message
        this.mErrorTextView.setVisibility(View.INVISIBLE);
        // We check if the username and password are present
        final Editable usernameEdit = mUsernameEditText.getText();
        if (TextUtils.isEmpty(usernameEdit)) {
            final String usernameMandatory = getResources().getString(R.string.mandatory_field_error, "username");
            Log.w(TAG_LOG, usernameMandatory);
            this.mErrorTextView.setText(usernameMandatory);
            this.mErrorTextView.setVisibility(View.VISIBLE);
            return;
        }
        final Editable passwordEdit = mPasswordEditText.getText();
        if (TextUtils.isEmpty(passwordEdit)) {
            final String passwordMandatory = getResources().getString(R.string.mandatory_field_error, "password");
            Log.w(TAG_LOG, passwordMandatory);
            this.mErrorTextView.setText(passwordMandatory);
            this.mErrorTextView.setVisibility(View.VISIBLE);
            return;
        }
        final Editable emailEdit = mEmailEditText.getText();
        if (TextUtils.isEmpty(emailEdit)) {
            final String emailMandatory = getResources().getString(R.string.mandatory_field_error, "email");
            Log.w(TAG_LOG, emailMandatory);
            this.mErrorTextView.setText(emailMandatory);
            this.mErrorTextView.setVisibility(View.VISIBLE);
            return;
        }
        // Data related to the birthDate
        int dayOfMonth = mBirthDatePicker.getDayOfMonth();
        int month = mBirthDatePicker.getMonth();
        int year = mBirthDatePicker.getYear();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        final long birthDate = cal.getTimeInMillis();
        // Data about the Location
        final Editable locationEdit = mLocationEditText.getText();
        String location = null;
        if (!TextUtils.isEmpty(locationEdit)) {
            location = locationEdit.toString();
        }
        // We get the info as Strings
        final String username = usernameEdit.toString();
        final String password = passwordEdit.toString();
        final String email = emailEdit.toString();
        final UserModel userModel = RegistrationService.get().register(username, password, email, birthDate, location);
        if (userModel != null) {
            Log.d(TAG_LOG, "User registered and authenticated!");
            // In this case the user is authenticated so we can return the UserData
            Intent resultIntent = new Intent();
            // If this userModel is not Serializable or Parcelable we'll get
            // a compilation error.
            resultIntent.putExtra(USER_DATA_EXTRA, userModel);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            // in this case the user is not authenticated so we show an error message
            this.mErrorTextView.setText(R.string.wrong_credential_error);
            this.mErrorTextView.setVisibility(View.VISIBLE);
        }
    }

}
