package it.folicana.milo.ugo.widget;

        import android.content.Context;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.FrameLayout;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import java.util.Date;
        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Locale;

        import it.folicana.milo.ugo.R;

/**
 * This is the class that describes the CalendarChooser custom component
 * <p/>
 * Created by Massimo Carli on 25/06/13.
 */
public class CalendarChooser extends FrameLayout {

    /**
     * This is the interface that must be implemented to be notify of the date selection
     */
    public interface OnCalendarChooserListener {

        /**
         * This is invoked when there's a selection in the dateChooser
         *
         * @param source       The CalendarChooser source of this selection
         * @param selectedDate The selectedDate
         */
        void dateSelected(CalendarChooser source, Date selectedDate);

    }

    /**
     * The listener for the CalendarChooserListener
     */
    private OnCalendarChooserListener mOnCalendarChooserListener;

    /**
     * A specific TextView that changes its background color changing its state
     */
    public static class DayTextView extends TextView {

        /**
         * The constant for the stat about the current day
         */
        private static final int[] STATE_TODAY = {R.attr.state_today};

        /**
         * The constant for the stat about the selected day
         */
        private static final int[] STATE_DAY_SELECTED = {R.attr.state_day_selected};

        /**
         * The constant for the stat about the current month
         */
        private static final int[] STATE_THIS_MONTH = {R.attr.state_this_month};

        /**
         * The constant for the stat about the other month
         */
        private static final int[] STATE_OTHER_MONTH = {R.attr.state_other_month};


        /**
         * If true the item is related to the current day
         */
        private boolean mToday = false;

        /**
         * If true the item is related to the current month but not the current day
         */
        private boolean mThisMonth = false;

        /**
         * If true the item is related to another month
         */
        private boolean mOtherMonth = false;

        /**
         * If true the item is related to the selected day
         */
        private boolean mSelectedDay = false;

        /**
         * The date for this item
         */
        private Calendar mItemDate;

        /**
         * Creates a DayTextView for the day item
         *
         * @param context The Context
         */
        public DayTextView(final Context context, final Calendar itemDate) {
            super(context);
            // We set the date for this item
            this.mItemDate = itemDate;
            // We set the background sensible to the custom states
            setBackgroundResource(R.drawable.calendar_day_bg);
        }

        /**
         * Set the thisDay state
         *
         * @param today If true this is the current day
         */
        public void setToday(final boolean today) {
            this.mToday = today;
            // In this case the states are mutual exclusive
            if (today) {
                mThisMonth = false;
                mOtherMonth = false;
                mSelectedDay = false;
            }
        }

        /**
         * Set the thisMonth state
         *
         * @param thisMonth If true this is the current month but not the current day
         */
        public void setThisMonth(final boolean thisMonth) {
            this.mThisMonth = thisMonth;
            // In this case the states are mutual exclusive
            if (thisMonth) {
                mToday = false;
                mOtherMonth = false;
                mSelectedDay = false;
            }
        }

        /**
         * Set the otherMonth state
         *
         * @param otherMonth If true this is a month different from the current
         */
        public void setOtherMonth(final boolean otherMonth) {
            this.mOtherMonth = otherMonth;
            // In this case the states are mutual exclusive
            if (otherMonth) {
                mToday = false;
                mThisMonth = false;
                mSelectedDay = false;
            }
        }

        /**
         * Set the selectedDay state
         *
         * @param selectedDay If true this is the selected day
         */
        public void setSelectedDay(final boolean selectedDay) {
            this.mSelectedDay = selectedDay;
            // In this case the states are mutual exclusive
            if (selectedDay) {
                mToday = false;
                mThisMonth = false;
                mOtherMonth = false;
            }
        }

        @Override
        protected int[] onCreateDrawableState(int extraSpace) {
            final int[] drawableState = super.onCreateDrawableState(extraSpace + 4);
            if (mToday) {
                mergeDrawableStates(drawableState, STATE_TODAY);
            }
            if (mSelectedDay) {
                mergeDrawableStates(drawableState, STATE_DAY_SELECTED);
            }
            if (mThisMonth) {
                mergeDrawableStates(drawableState, STATE_THIS_MONTH);
            }
            if (mOtherMonth) {
                mergeDrawableStates(drawableState, STATE_OTHER_MONTH);
            }
            return drawableState;
        }

        /**
         * @return The date for this item
         */
        public Calendar getItemDate() {
            return mItemDate;
        }
    }


    /**
     * This is the listener we register to every item to select a date
     */
    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            // We get the selected date
            mCurrentDate = ((DayTextView) view).getItemDate();
            Log.i(TAG_LOG, "SELECTED " + mCurrentDate.getTime());
            // We update the UI
            updateUI();
        }
    };


    /**
     * The Tag for the Log
     */
    private static final String TAG_LOG = CalendarChooser.class.getName();

    /**
     * The padding for the day names
     */
    private static final int DAY_PADDING = 10;

    /**
     * The number of days in a week
     */
    private static final int DAYS_IN_A_WEEK = 7;

    /**
     * The DateFormatter for the name of the current month
     */
    private static final DateFormat MONTH_DATE_FORMAT = new SimpleDateFormat("MMMM yyyy");

    /**
     * The format for the day in the week
     */
    private static final DateFormat DAY_IN_WEEK_FORMAT = new SimpleDateFormat("E");

    /**
     * The date currently shown
     */
    private Calendar mCurrentDate;

    /**
     * The TextView for the month
     */
    private TextView mMonthTextView;

    /**
     * The RelativeLayout container for the names of the days
     */
    private LinearLayout mDayNamesContainer;

    /**
     * The RelativeLayout container for the days
     */
    private LinearLayout mDaysContainer;

    /**
     * Creates a CalendarChooser programmatically
     *
     * @param context The Context
     */
    public CalendarChooser(Context context) {
        super(context);
        // We set the current Date
        mCurrentDate = Calendar.getInstance(Locale.getDefault());
        // Let's init the UI
        init();
    }

    /**
     * Called when we create the CalendarChooser putting him into a template
     *
     * @param context The Context
     * @param attrs   The layout attributes
     */
    public CalendarChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        // We set the current Date
        mCurrentDate = Calendar.getInstance(Locale.getDefault());
        // Let's init the UI
        init();
    }


    /**
     * This method creates the UI for this component
     */
    private void init() {
        // Here we have to inflate the layout putting it into this FrameLayout
        RelativeLayout calendarView = (RelativeLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.widget_calendar_chooser, null);
        // We get the reference to the TextView for the month
        mMonthTextView = (TextView) calendarView.findViewById(R.id.calendar_month_label);
        // We get the reference to the buttons updating the current date
        calendarView.findViewById(R.id.calendar_previous_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here we have to update the model
                mCurrentDate.add(Calendar.MONTH, -1);
                // and update the UI
                updateUI();
            }
        });
        calendarView.findViewById(R.id.calendar_next_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here we have to update the model
                mCurrentDate.add(Calendar.MONTH, 1);
                // and update the UI
                updateUI();
            }
        });
        // The containers for the days
        mDaysContainer = (LinearLayout) calendarView.findViewById(R.id.calendar_days_container);
        // The container for the names of the days
        mDayNamesContainer = (LinearLayout) calendarView.findViewById(R.id.calendar_name_days_container);
        // We add it to this layout
        addView(calendarView);
        // We update the UI using the current date
        updateUI();
    }


    /**
     * This method set the current date for this CalendarChooser
     *
     * @param date The current date
     */
    public void setCurrentDate(final Calendar date) {
        this.mCurrentDate = date;
        // We update the UI
        updateUI();
    }

    /**
     * This method set the current date for this CalendarChooser as a Date Object
     *
     * @param date The current date
     */
    public void setCurrentDate(final Date date) {
        final Calendar newDate = Calendar.getInstance();
        newDate.setTime(date);
        setCurrentDate(newDate);
    }

    /**
     * This method set the current date for this CalendarChooser as a long
     *
     * @param dateTimeMillis The current date as long
     */
    public void setCurrentDate(final long dateTimeMillis) {
        final Calendar newDate = Calendar.getInstance();
        newDate.setTimeInMillis(dateTimeMillis);
        setCurrentDate(newDate);
    }

    /**
     * @return The selectedDate
     */
    public Calendar getCurrentDate() {
        return mCurrentDate;
    }

    /**
     * @return The selectedDate as a Date object
     */
    public Date getCurrentDateAsDate() {
        return mCurrentDate.getTime();
    }

    /**
     * Set the listener for this CalendarChooser
     *
     * @param onCalendarChooserListener The listener for date selection
     */
    public void setOnCalendarChooserListener(final OnCalendarChooserListener onCalendarChooserListener) {
        this.mOnCalendarChooserListener = onCalendarChooserListener;
    }

    /**
     * This utility method updates the UI when the current date changes
     */
    private void updateUI() {
        // We remove all items from layout
        mDayNamesContainer.removeAllViews();
        mDaysContainer.removeAllViews();
        // We set the value for the current month
        final String currentMonth = MONTH_DATE_FORMAT.format(mCurrentDate.getTime());
        mMonthTextView.setText(currentMonth.toUpperCase());
        // Here we have to update the grid of the months. To do that we need to know how many
        // weeks are covered by the month. We use the week information of the Calendar object
        int maxWeekNumber = mCurrentDate.getActualMaximum(Calendar.WEEK_OF_MONTH);
        // We have to fill a grid that has a number of cols as the numbers of days
        // and a number of rows as the number of weeks + 1 (the row for the day names).
        // We'll play on the LayoutParams to put the position of these components
        // we start with the row of the day names. We create a LayoutParams that divide the space in equal parts
        final LinearLayout.LayoutParams dayNamesLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        dayNamesLp.weight = 1;
        // The first day of the week
        final int firstDayInTheWeek = mCurrentDate.getFirstDayOfWeek();
        final Calendar utilCalendar = Calendar.getInstance();
        for (int i = 0; i < DAYS_IN_A_WEEK; i++) {
            // The current day in the week
            final int currentDay = (firstDayInTheWeek + i) % DAYS_IN_A_WEEK;
            utilCalendar.set(Calendar.DAY_OF_WEEK, currentDay);
            final String shortDayName = DAY_IN_WEEK_FORMAT.format(utilCalendar.getTime());
            // We create the TextView for the day names
            final TextView dayTextView = new TextView(getContext());
            dayTextView.setGravity(Gravity.CENTER);
            dayTextView.setPadding(DAY_PADDING, DAY_PADDING, DAY_PADDING, DAY_PADDING);
            dayTextView.setLayoutParams(dayNamesLp);
            dayTextView.setText(shortDayName);
            mDayNamesContainer.addView(dayTextView);
        }
        // We need to know which is the first day of the first row in order to apply an offset of each day
        final Calendar firstOfTheMonth = Calendar.getInstance();
        firstOfTheMonth.setTime(mCurrentDate.getTime());
        firstOfTheMonth.set(Calendar.DAY_OF_MONTH, 1);
        int firstDay = firstOfTheMonth.get(Calendar.DAY_OF_WEEK);
        final int dayOffset = firstDay - firstDayInTheWeek;
        // Now we have to fill the days with a similar criteria
        final LinearLayout.LayoutParams dayRowLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        utilCalendar.setTime(firstOfTheMonth.getTime());
        utilCalendar.add(Calendar.DATE, -1 * dayOffset);
        final Calendar now = Calendar.getInstance();
        final int today = now.get(Calendar.DAY_OF_YEAR);
        final int todayYear = now.get(Calendar.YEAR);
        final int selectedDay = mCurrentDate.get(Calendar.DAY_OF_YEAR);
        final int selectedMonth = mCurrentDate.get(Calendar.MONTH);
        final int selectedYear = mCurrentDate.get(Calendar.YEAR);
        for (int week = 0; week < maxWeekNumber; week++) {
            final LinearLayout dayRow = new LinearLayout(getContext());
            dayRow.setLayoutParams(dayRowLp);
            mDaysContainer.addView(dayRow);
            // For every week we create a LinearLayout for the days
            for (int i = 0; i < DAYS_IN_A_WEEK; i++) {
                // We create the object for the state of the current item
                final Calendar itemDate = Calendar.getInstance();
                itemDate.setTime(utilCalendar.getTime());
                // We have to understand which is the day related to the current position in the grid
                final DayTextView dayTextView = new DayTextView(getContext(), itemDate);
                dayTextView.setGravity(Gravity.CENTER);
                dayTextView.setPadding(DAY_PADDING, DAY_PADDING, DAY_PADDING, DAY_PADDING);
                dayTextView.setLayoutParams(dayNamesLp);
                int dayToShow = utilCalendar.get(Calendar.DATE);
                dayTextView.setText("" + dayToShow);
                // We set the state
                final int currentDayInYear = utilCalendar.get(Calendar.DAY_OF_YEAR);
                final int currentMonthYear = utilCalendar.get(Calendar.MONTH);
                final int currentYear = utilCalendar.get(Calendar.YEAR);
                // We check if the current day is the selected day
                if (selectedDay == currentDayInYear && selectedYear == currentYear) {
                    // The current day is the selected one. It has precedence over the
                    // today
                    dayTextView.setSelectedDay(true);
                } else if (today == currentDayInYear && todayYear == currentYear) {
                    // In this case it's today
                    dayTextView.setToday(true);
                } else if (currentMonthYear == selectedMonth) {
                    // The day os a day of the selected month
                    dayTextView.setThisMonth(true);
                } else {
                    // The day is not of the selected month. It's a day of the previous
                    // or successive month
                    dayTextView.setOtherMonth(true);
                }
                // We manage events
                dayTextView.setClickable(true);
                dayTextView.setOnClickListener(mOnClickListener);
                // We add the item to the View
                dayRow.addView(dayTextView);
                // We update the date
                utilCalendar.add(Calendar.DATE, 1);
            }
        }

    }


}
