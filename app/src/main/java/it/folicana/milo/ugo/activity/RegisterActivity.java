package it.folicana.milo.ugo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.folicana.milo.ugo.R;
import it.folicana.milo.ugo.conf.Const;
import it.folicana.milo.ugo.model.UserModel;
import it.folicana.milo.ugo.service.RegistrationService;
import it.folicana.milo.ugo.widget.CalendarGraphChooserDialogFragment;

/**
 * This is the Activity we use to manage the Login
 * <p/>
 * Created by Massimo Carli on 31/05/2013.
 */
public class RegisterActivity extends FragmentActivity implements CalendarGraphChooserDialogFragment.OnGraphCalendarChooserListener {

    /**
     * The Tag of the Log for this class
     */
    private static final String TAG_LOG = RegisterActivity.class.getName();

    /**
     * The DateFormatter for the name of the current month
     */
    private static final DateFormat BIRTH_DATE_FORMAT = new SimpleDateFormat("dd MMMM yyyy");

    /**
     * The action we use to manage the Registration
     */
    public static final String REGISTRATION_ACTION = Const.PKG + ".action.REGISTRATION_ACTION";

    /**
     * The key for the extra that we use to get the UserData after registration
     */
    public static final String USER_DATA_EXTRA = Const.PKG + ".extra.USER_DATA_EXTRA";

    /**
     * THe key we use for the state of the currentBirthDate
     */
    public static final String BIRTH_DATE_KEY = Const.PKG + ".extra.BIRTH_DATE_KEY";

    /**
     * The reference to the EditText for the username
     */
    private EditText mUsernameEditText;

    /**
     * The reference to the EditText for the password
     */
    private EditText mPasswordEditText;

    /**
     * The reference to the EditText for the email
     */
    private EditText mEmailEditText;

    /**
     * The reference to the EditText for the location
     */
    private EditText mLocationEditText;

    /**
     * The reference to the TextView for the error messages
     */
    private TextView mErrorTextView;

    /**
     * The reference to the TextView for the birthdate
     */
    private TextView mBirthdateTextView;

    /**
     * The current selected BirthDate
     */
    private Date mSelectedBirthDate;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // We get the reference of the EditText
        mUsernameEditText = (EditText) findViewById(R.id.username_edittext);
        mPasswordEditText = (EditText) findViewById(R.id.password_edittext);
        mEmailEditText = (EditText) findViewById(R.id.email_edittext);
        mLocationEditText = (EditText) findViewById(R.id.location_edittext);
        // The TextView for the birthDate
        mBirthdateTextView = (TextView) findViewById(R.id.birthdate_datepicker);
        // The reference to the TextView for error
        mErrorTextView = (TextView) findViewById(R.id.error_message_label);
        // The reference to the registerButton
        final Button registerButton = (Button) findViewById(R.id.register_button);
        // The current BirthDate
        if (savedInstanceState == null) {
            mSelectedBirthDate = new Date();
        } else {
            mSelectedBirthDate = new Date();
            final long savedBirthDate = savedInstanceState.getLong(BIRTH_DATE_KEY, System.currentTimeMillis());
            mSelectedBirthDate.setTime(savedBirthDate);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // We save the BirthDate
        outState.putLong(BIRTH_DATE_KEY, mSelectedBirthDate.getTime());
    }

    /**
     * This is invoked when we press the registration button
     *
     * @param regButton The Button pressed
     */
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
        final UserModel userModel = RegistrationService.get().register(username, password, email, mSelectedBirthDate.getTime(), location);
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


    /**
     * Invoked when we show the Dialog for the date selection
     *
     * @param button The button selected to show the Dialog
     */
    public void selectDate(View button) {
        CalendarGraphChooserDialogFragment dateDialog = CalendarGraphChooserDialogFragment.getCalendarChooserDialog(mSelectedBirthDate);
        dateDialog.show(getSupportFragmentManager(), "DATE SELECTION TAG");
    }

    @Override
    public void dateSelected(CalendarGraphChooserDialogFragment source, Date selectedDate) {
        Log.d(TAG_LOG, "In RegisterActivity the selected date is : " + selectedDate);
        this.mSelectedBirthDate = selectedDate;
        mBirthdateTextView.setText(BIRTH_DATE_FORMAT.format(mSelectedBirthDate));
    }

    @Override
    public void selectionCanceled(CalendarGraphChooserDialogFragment source) {
        Log.d(TAG_LOG, "In RegisterActivity the user canceled date selection");
    }
}