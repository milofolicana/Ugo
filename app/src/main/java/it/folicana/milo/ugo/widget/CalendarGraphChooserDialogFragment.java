package it.folicana.milo.ugo.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import it.folicana.milo.ugo.R;

import java.util.Date;

import it.folicana.milo.ugo.conf.Const;

/**
 * Created by Massimo Carli on 26/06/13.
 */
public class CalendarGraphChooserDialogFragment extends DialogFragment {

    /**
     * The Tag for the log
     */
    private static final String TAG_LOG = CalendarGraphChooserDialogFragment.class.getName();


    /**
     * The key we use for the date argument
     */
    private static final String DATE_ARG_KEY = Const.PKG + ".args.DATE_ARG_KEY";

    /**
     * This is the interface that must be implemented to be notify of the date selection
     */
    public interface OnGraphCalendarChooserListener {

        /**
         * This is invoked when there's a selection in the dateChooser
         *
         * @param source       The CalendarChooserDialogFragment source of this selection
         * @param selectedDate The selectedDate
         */
        void dateSelected(CalendarGraphChooserDialogFragment source, Date selectedDate);

        /**
         * Invoked to notify the cancel of the selection
         *
         * @param source The CalendarChooserDialogFragment source of this action
         */
        void selectionCanceled(CalendarGraphChooserDialogFragment source);

    }

    /**
     * The listener for the OnGraphCalendarChooserListener
     */
    private OnGraphCalendarChooserListener mOnGraphCalendarChooserListener;

    /**
     * Creates and returns a Dialog for the choice of a Date
     *
     * @param currentDate The initial date. If null we use the current date
     * @return The CalendarChooserDialogFragment to select a Date
     */
    public static CalendarGraphChooserDialogFragment getCalendarChooserDialog(final Date currentDate) {
        // We create the DialogFragment
        CalendarGraphChooserDialogFragment calendarChooserDialog = new CalendarGraphChooserDialogFragment();
        // We create the arguments for the Date passing through a long
        Bundle args = new Bundle();
        if (currentDate == null) {
            args.putLong(DATE_ARG_KEY, System.currentTimeMillis());
        } else {
            args.putLong(DATE_ARG_KEY, currentDate.getTime());
        }
        calendarChooserDialog.setArguments(args);
        // We return the DialogFragment
        return calendarChooserDialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnGraphCalendarChooserListener) {
            this.mOnGraphCalendarChooserListener = (OnGraphCalendarChooserListener) activity;
        }
    }

    /**
     * Creates and returns a Dialog for the choice of a Date
     *
     * @return The CalendarChooserDialogFragment starting at the current Date
     */
    public static CalendarGraphChooserDialogFragment getCalendarChooserDialog() {
        // Passing null we use the currentDate
        return getCalendarChooserDialog(null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Here we create the CalendarChooser and insert into the Dialog
        final GraphCalendarChooser calendarChooser = new GraphCalendarChooser(getActivity());
        // We get the currentDate into the arguments
        final long dateAsLong = getArguments().getLong(DATE_ARG_KEY);
        calendarChooser.setCurrentDate(dateAsLong);
        // Here we create and return the AlertDialog using a Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.calendar_dialog_title)
                .setView(calendarChooser)
                .setPositiveButton(R.string.calendar_yes_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // We notify the new date
                        if (mOnGraphCalendarChooserListener != null) {
                            mOnGraphCalendarChooserListener.dateSelected(CalendarGraphChooserDialogFragment.this,
                                    calendarChooser.getCurrentDateAsDate());
                        }
                        Log.d(TAG_LOG, "Date selected " + calendarChooser.getCurrentDateAsDate());
                    }
                })
                .setNegativeButton(R.string.calendar_no_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Notify the cancel
                        if (mOnGraphCalendarChooserListener != null) {
                            mOnGraphCalendarChooserListener.selectionCanceled(CalendarGraphChooserDialogFragment.this);
                        }
                        Log.d(TAG_LOG, "Selection canceled");
                    }
                });
        return builder.create();
    }
}
