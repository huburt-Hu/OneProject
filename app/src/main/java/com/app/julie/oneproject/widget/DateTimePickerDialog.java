package com.app.julie.oneproject.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.FrameLayout;

import com.aigestudio.wheelpicker.WheelPicker;
import com.app.julie.oneproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/12.
 */

public class DateTimePickerDialog extends UpDialog implements View.OnClickListener {

    private MaterialCalendarView calendarView;
    private FrameLayout container;
    private View timePicker;
    private Calendar mCalendar;
    private WheelPicker wheelHour;
    private WheelPicker wheelMinute;
    private boolean showTime = true;

    private OnDateSelectedListener listener;
    private TabLayout tabLayout;
    private FrameLayout.LayoutParams params;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_date_time_picker;
    }

    public DateTimePickerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onViewCreated(View view) {
        container = (FrameLayout) view.findViewById(R.id.fl_container);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        btnOk.setOnClickListener(this);

        params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mCalendar = Calendar.getInstance();
        refreshView(mCalendar, showTime);



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                container.removeAllViews();
                switch (tab.getPosition()) {
                    case 0:
                        container.addView(calendarView, params);
                        break;
                    case 1:
                        container.addView(timePicker, params);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void refreshView(Calendar calendar, boolean showTime) {
        if (calendar == null)
            calendar = mCalendar;
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText(calendar.get(Calendar.YEAR) + "/"
                + (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.DATE)));
        createCalenderVIew(tabLayout.getTabAt(0), calendar);
        container.addView(calendarView, params);
        if (showTime) {
            tabLayout.addTab(tabLayout.newTab().setText(calendar.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendar.get(Calendar.MINUTE)));
            createTimePicker(tabLayout.getTabAt(1), calendar);
        }
    }

    private void createTimePicker(final TabLayout.Tab tab, Calendar calendar) {
        if (timePicker == null) {
            timePicker = LayoutInflater.from(container.getContext()).inflate(R.layout.item_time_picker, container, false);
            wheelHour = (WheelPicker) timePicker.findViewById(R.id.wheel_hour);
            wheelMinute = (WheelPicker) timePicker.findViewById(R.id.wheel_minute);
        }
        ViewGroup parent = (ViewGroup) timePicker.getParent();
        if (parent != null)
            parent.removeView(timePicker);

        List<String> hours = new ArrayList<String>();
        List<String> minutes = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            hours.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        wheelHour.setData(hours);
        for (int i = 0; i < 60; i++) {
            minutes.add(i < 10 ? "0" + i : String.valueOf(i));
        }
        wheelMinute.setData(minutes);

        wheelHour.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                tab.setText(String.valueOf(data) + ":"
                        + String.valueOf(wheelMinute.getData().get(wheelMinute.getCurrentItemPosition())));
            }
        });
        wheelMinute.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                tab.setText(String.valueOf(wheelHour.getData().get(wheelHour.getCurrentItemPosition())
                        + ":" + String.valueOf(data)));
            }
        });
        wheelHour.setSelectedItemPosition(calendar.get(Calendar.HOUR_OF_DAY));
        wheelMinute.setSelectedItemPosition(calendar.get(Calendar.MINUTE));
    }

    private void createCalenderVIew(final TabLayout.Tab tab, Calendar calendar) {
        if (calendarView == null)
            calendarView = new MaterialCalendarView(container.getContext());
        ViewGroup parent = (ViewGroup) calendarView.getParent();
        if (parent != null)
            parent.removeView(calendarView);

//        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        calendarView.setSelectionColor(Color.parseColor("#3294f1"));
        calendarView.setTileHeight(0);
//        calendarView.setTopbarVisible(false);
        calendarView.setOnDateChangedListener(new com.prolificinteractive.materialcalendarview.OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                tab.setText(date.getYear() + "/" + (date.getMonth() + 1) + "/" + date.getDay());
            }
        });
        calendarView.clearSelection();
        calendarView.setDateSelected(calendar, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                CalendarDay date = calendarView.getCurrentDate();
                if (wheelHour != null && wheelMinute != null) {
                    mCalendar.set(date.getYear(), date.getMonth() + 1, date.getDay()
                            , Integer.valueOf((String) wheelHour.getData().get(wheelHour.getCurrentItemPosition()))
                            , Integer.valueOf((String) wheelMinute.getData().get(wheelMinute.getCurrentItemPosition())));
                } else {
                    mCalendar.set(date.getYear(), date.getMonth() + 1, date.getDay());
                }
                if (listener != null) {
                    listener.onDateSelected(mCalendar);
                }
                dismiss();
                break;
        }
    }

    public void show(boolean showTime) {
        show(null, showTime);
    }

    public void show(Calendar calendar, boolean showTime) {
        super.show();
        refreshView(calendar, showTime);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Calendar calendar);
    }

}
