package it.folicana.milo.ugo.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import it.folicana.milo.ugo.R;

/**
 * This is the class that describes the CalendarChooser custom component
 * <p/>
 * Created by Massimo Carli on 25/06/13.
 */
public class GraphCalendarChooser extends FrameLayout {

    /**
     * The Tag for the Log
     */
    private static final String TAG_LOG = GraphCalendarChooser.class.getName();

    /**
     * This is the interface that must be implemented to be notify of the date selection
     */
    public interface OnGraphCalendarChooserListener {

        /**
         * This is invoked when there's a selection in the dateChooser
         *
         * @param source       The GraphCalendarChooser source of this selection
         * @param selectedDate The selectedDate
         */
        void dateSelected(GraphCalendarChooser source, Date selectedDate);

    }

    /**
     * The listener for the GraphCalendarChooser
     */
    private OnGraphCalendarChooserListener mOnGraphCalendarChooserListener;

    /**
     * The Class that draws the grid for a given month
     */
    private class CalendarGrid extends View {

        /**
         * The height of every day
         */
        private static final int DAY_HEIGHT = 80;

        /**
         * The size of the text for the day names
         */
        private static final float DAY_NAMES_TEXT_SIZE = 35.0f;

        /**
         * The Paint for the bg of the days
         */
        private Paint mBgPaint;

        /**
         * The Paint for the name of the days
         */
        private Paint mDayNamesPaint;

        /**
         * The Paint for the days
         */
        private Paint mDaysPaint;

        /**
         * The Paint for the days
         */
        private Paint mCurrentDayBgPaint;

        /**
         * The bg color for today
         */
        private int mBgTodayColor;

        /**
         * The color for this month but not today
         */
        private int mBgColorThisMonth;

        /**
         * The color for other month
         */
        private int mBgColorOtherMonth;

        /**
         * The color for the selected day
         */
        private int mBgColorSelected;

        /**
         * This is the first date shown. We use this to calculate the date
         * after a touch event
         */
        private Calendar mFirstDateShown;

        /**
         * The Date we use for touch events
         */
        private Calendar mMovingDate;

        /**
         * Creates the CalendarGrid for the drawing of the days
         *
         * @param context The Context
         */
        private CalendarGrid(Context context) {
            super(context);
            // At the beginning, the moving date is the current
            mMovingDate = mCurrentDate;
            // we read colors from the resources
            final Resources resources = context.getResources();
            mBgTodayColor = resources.getColor(R.color.calendar_today_bg_color);
            mBgColorThisMonth = resources.getColor(R.color.calendar_this_month_bg_color);
            mBgColorOtherMonth = resources.getColor(R.color.calendar_other_month_bg_color);
            mBgColorSelected = resources.getColor(R.color.calendar_selected_bg_color);
            // We init the Paint for the bg
            mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBgPaint.setColor(Color.WHITE);
            // We init the Paint for the day names text
            mDayNamesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDayNamesPaint.setColor(Color.BLACK);
            mDayNamesPaint.setFakeBoldText(true);
            mDayNamesPaint.setTextSize(DAY_NAMES_TEXT_SIZE);
            // We init the Paint for the days
            mDaysPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDaysPaint.setColor(Color.BLACK);
            mDaysPaint.setTextSize(DAY_NAMES_TEXT_SIZE);
            // The Paint for the current month bg
            mCurrentDayBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCurrentDayBgPaint.setColor(Color.GREEN);
            mCurrentDayBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mCurrentDayBgPaint.setStrokeWidth(2.0f);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            // We use the same spec as the width. For the height we multiply the number of weeks for the
            // height of a single week
            // Here we have to update the grid of the months. To do that we need to know how many
            // weeks are covered by the month. We use the week information of the Calendar object
            final int maxWeekNumber = mCurrentDate.getActualMaximum(Calendar.WEEK_OF_MONTH);
            DisplayMetrics dm = new DisplayMetrics();
            // The +1 is for the day names
            final int daysHeight = (maxWeekNumber + 1) * DAY_HEIGHT;
            int heightSpec = MeasureSpec.makeMeasureSpec(daysHeight, MeasureSpec.EXACTLY);
            setMeasuredDimension(widthMeasureSpec, heightSpec);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            // We share the width in 7 parts
            final float itemWidth = (float) getWidth() / DAYS_IN_A_WEEK;
            canvas.drawRect(0, 0, getWidth(), getHeight(), mBgPaint);
            // The first day of the week
            final int firstDayInTheWeek = mCurrentDate.getFirstDayOfWeek();
            final Calendar utilCalendar = Calendar.getInstance();
            for (int i = 0; i < DAYS_IN_A_WEEK; i++) {
                // The current day in the week
                final int currentDay = (firstDayInTheWeek + i) % DAYS_IN_A_WEEK;
                utilCalendar.set(Calendar.DAY_OF_WEEK, currentDay);
                final String shortDayName = DAY_IN_WEEK_FORMAT.format(utilCalendar.getTime());
                // We have to center the name so we have to know how width are they with the
                // current font
                Rect textNameBounds = new Rect();
                mDayNamesPaint.getTextBounds(shortDayName, 0, shortDayName.length(), textNameBounds);
                int nameHeight = (int) (mDayNamesPaint.ascent() - mDayNamesPaint.descent());
                int nameWidth = textNameBounds.width();
                // The x and y of the label
                final float nameX = i * itemWidth + (itemWidth - nameWidth) / 2;
                final float nameY = (DAY_HEIGHT - nameHeight) / 2;
                // We draw the name
                canvas.drawText(shortDayName, nameX, nameY, mDayNamesPaint);
            }
            // The max number of weeks
            final int maxWeekNumber = mCurrentDate.getActualMaximum(Calendar.WEEK_OF_MONTH);
            // We need to know which is the first day of the first row in order to apply an offset of each day
            final Calendar firstOfTheMonth = Calendar.getInstance();
            firstOfTheMonth.setTime(mCurrentDate.getTime());
            firstOfTheMonth.set(Calendar.DAY_OF_MONTH, 1);
            int firstDay = firstOfTheMonth.get(Calendar.DAY_OF_WEEK);
            final int dayOffset = firstDay - firstDayInTheWeek;
            // Now we have to fill the days with a similar criteria
            utilCalendar.setTime(firstOfTheMonth.getTime());
            utilCalendar.add(Calendar.DATE, -1 * dayOffset);
            // We save the first date shown for later usage in event management
            mFirstDateShown = Calendar.getInstance();
            mFirstDateShown.setTime(utilCalendar.getTime());
            // Make calculations...
            final Calendar now = Calendar.getInstance();
            final int today = now.get(Calendar.DAY_OF_YEAR);
            final int todayMonth = now.get(Calendar.MONTH);
            final int todayYear = now.get(Calendar.YEAR);
            final int selectedDay = mCurrentDate.get(Calendar.DAY_OF_YEAR);
            final int selectedMonth = mCurrentDate.get(Calendar.MONTH);
            final int selectedYear = mCurrentDate.get(Calendar.YEAR);
            for (int week = 0; week < maxWeekNumber; week++) {
                // For every week we create a LinearLayout for the days
                for (int i = 0; i < DAYS_IN_A_WEEK; i++) {
                    // We have to understand which is the day related to the current position in the grid
                    final int dayToShow = utilCalendar.get(Calendar.DATE);
                    final String dayAsString = String.valueOf(dayToShow);
                    // We calculate the x and the y of the day text
                    Rect textNameBounds = new Rect();
                    mDaysPaint.getTextBounds(dayAsString, 0, dayAsString.length(), textNameBounds);
                    final int dayHeight = (int) (mDayNamesPaint.ascent() - mDayNamesPaint.descent());
                    final int dayWidth = textNameBounds.width();
                    final float rectX = i * itemWidth;
                    final float rectY = (week + 1) * DAY_HEIGHT;
                    final int dayX = (int) ((i * itemWidth) + (itemWidth - dayWidth) / 2);
                    // The 2 is because the y is related to the bottom on the cell for a day (+1) and
                    // because the have a line for the day names
                    final int dayY = (week + 1) * DAY_HEIGHT + (DAY_HEIGHT - dayHeight) / 2;
                    // We draw the background for the day based in its selection. We calculate
                    // the box to draw
                    final int currentDayInYear = utilCalendar.get(Calendar.DAY_OF_YEAR);
                    final int currentMonthYear = utilCalendar.get(Calendar.MONTH);
                    final int currentYear = utilCalendar.get(Calendar.YEAR);
                    if (selectedDay == currentDayInYear && selectedYear == currentYear) {
                        // The current day is the selected one. It has precedence over the
                        // today
                        mCurrentDayBgPaint.setColor(mBgColorSelected);
                    } else if (today == currentDayInYear && todayYear == currentYear) {
                        // In this case it's today
                        mCurrentDayBgPaint.setColor(mBgTodayColor);
                    } else if (currentMonthYear == selectedMonth) {
                        // The day os a day of the selected month
                        mCurrentDayBgPaint.setColor(mBgColorThisMonth);
                    } else {
                        // The day is not of the selected month. It's a day of the previous
                        // or successive month
                        mCurrentDayBgPaint.setColor(mBgColorOtherMonth);
                    }
                    canvas.drawRect(rectX, rectY, rectX + itemWidth, rectY + DAY_HEIGHT, mCurrentDayBgPaint);
                    // We draw the day
                    canvas.drawText(dayAsString, dayX, dayY, mDaysPaint);
                    // We update the date
                    utilCalendar.add(Calendar.DATE, 1);
                }
            }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // We get the coordinates for the touched events
            float touchedX = event.getX();
            // We take into account the height for the names
            float touchedY = event.getY() - DAY_HEIGHT;
            int action = event.getAction();
            final float itemWidth = (float) getWidth() / DAYS_IN_A_WEEK;
            // we calculate the new date and update it
            final int touchedCols = (int) (touchedX / itemWidth);
            final int touchedRows = (int) (touchedY / DAY_HEIGHT);
            final int offset = touchedCols + touchedRows * DAYS_IN_A_WEEK;
            //int offset = (int) ((touchedX / itemWidth) + (touchedY / DAY_HEIGHT) * DAYS_IN_A_WEEK);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mMovingDate.setTime(mFirstDateShown.getTime());
                    mMovingDate.add(Calendar.DATE, offset);
                    mCurrentDate = mMovingDate;
                    postInvalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!mMovingDate.equals(mCurrentDate)) {
                        // We update the date
                        mCurrentDate = mMovingDate;
                        postInvalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
            }
            return true;
        }
    }

    /**
     * The padding for the day names
     */
    private static final int DAY_NAMES_PADDING = 7;

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
     * The RelativeLayout container for the days
     */
    private FrameLayout mDaysContainer;

    /**
     * The CalendarGrid for the days display
     */
    private CalendarGrid mCalendarGrid;

    /**
     * Creates a CalendarChooser programmatically
     *
     * @param context The Context
     */
    public GraphCalendarChooser(Context context) {
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
    public GraphCalendarChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        // We set the current Date
        mCurrentDate = Calendar.getInstance(Locale.getDefault());
        // Let's init the UI
        init();
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
     * Set the listener for this GraphCalendarChooser
     *
     * @param onGraphCalendarChooserListener The listener for date selection
     */
    public void setOnCalendarChooserListener(final OnGraphCalendarChooserListener onGraphCalendarChooserListener) {
        this.mOnGraphCalendarChooserListener = onGraphCalendarChooserListener;
    }


    /**
     * This method creates the UI for this component
     */
    private void init() {
        // Here we have to inflate the layout putting it into this FrameLayout
        RelativeLayout calendarView = (RelativeLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.widget_graph_calendar_chooser, null);
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
        mDaysContainer = (FrameLayout) calendarView.findViewById(R.id.calendar_days_container);
        // We add it to this layout
        addView(calendarView);
        // We update the UI using the current date
        updateUI();
    }

    /**
     * This utility method updates the UI when the current date changes
     */
    private void updateUI() {
        // We set the value for the current month
        final String currentMonth = MONTH_DATE_FORMAT.format(mCurrentDate.getTime());
        mMonthTextView.setText(currentMonth.toUpperCase());
        // We add the CalendarGrid of not done
        if (mCalendarGrid == null) {
            mCalendarGrid = new CalendarGrid(getContext());
            final LinearLayout.LayoutParams dayGridLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.MATCH_PARENT);
            mCalendarGrid.setLayoutParams(dayGridLp);
            mDaysContainer.addView(mCalendarGrid);
        } else {
            mCalendarGrid.postInvalidate();
        }

    }


}
