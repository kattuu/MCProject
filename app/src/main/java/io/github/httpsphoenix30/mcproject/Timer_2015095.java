package io.github.httpsphoenix30.mcproject;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.github.httpsphoenix30.mcproject.receivers.AlarmReceiver;

/**
 * Created by vishal on 28/04/18.
 */

public class Timer_2015095 extends Fragment {

    public static final String PREF_NAME = "MC_PREF";
    public static final String START_TIME_HR = "START_TIME_HR";
    public static final String START_TIME_MIN = "START_TIME_MIN";
    public static final String END_TIME_HR = "END_TIME_HR";
    public static final String END_TIME_MIN = "END_TIME_MIN";
    public static final String IS_ALARM_SET = "IS_ALARM_SET";

    static final int TIME_DIALOG_ID = 1111;
    static final int TIME_DIALOG_ID_END = 2222;
    public static final int RC_ALARM = 100;
    private TextView view_start;
    private TextView view_end;
    public Button btnClick_start;
    public Button btnClick_end;
    private Button btnSetAlarms;
    private int flag = -1;
    private int hr_start;
    private int hr_end;
    private int min_start;
    private int min_end;
    private Date start_date;
    private Date end_date;
    private Context mContext;
    private LocationManager locationManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {

        View frag_view = inflater.inflate(R.layout.fragment_timer_2015095, viewGroup, false);

        btnSetAlarms = frag_view.findViewById(R.id.btn_set);
        view_start = (TextView) frag_view.findViewById(R.id.output_start);
        view_end = (TextView) frag_view.findViewById(R.id.output_end);
        //final Calendar c = Calendar.getInstance();
        //hr = c.get(Calendar.HOUR_OF_DAY);
        //min = c.get(Calendar.MINUTE);
        //updateTime(hr, min);

        addButtonClickListener(frag_view);

        return frag_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void addButtonClickListener(View view) {
        btnClick_start = (Button) view.findViewById(R.id.btnClick_start);
        btnClick_end = (Button) view.findViewById(R.id.btnClick_end);
        btnClick_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                createdDialog(TIME_DIALOG_ID).show();
            }
        });
        btnClick_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                createdDialog(TIME_DIALOG_ID).show();
            }
        });

        btnSetAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                setAlarms(sharedPreferences.getInt(START_TIME_HR, 0), sharedPreferences.getInt(START_TIME_MIN, 0));
                sharedPreferences.edit().putBoolean(IS_ALARM_SET, true).apply();
            }
        });
    }

    protected Dialog createdDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(getActivity(), timePickerListener, hr_start, min_start, false);
            case TIME_DIALOG_ID_END:
                return new TimePickerDialog(getActivity(), timePickerListener, hr_end, min_end, true);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            //hr = hourOfDay;
            //min = minutes;
            //updateTime(hr, min);
            if (flag == 0) {

                hr_start = hourOfDay;
                min_start = minutes;

                updateTime(hr_start, min_start);

                mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
                        .putInt(START_TIME_HR, hourOfDay)
                        .putInt(START_TIME_MIN, minutes)
                        .apply();
            } else if (flag == 1) {

                hr_end = hourOfDay;
                min_end = minutes;

                updateTime(hr_end, min_end);

                mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit()
                        .putInt(END_TIME_HR, hourOfDay)
                        .putInt(END_TIME_MIN, minutes)
                        .apply();
            }
        }
    };

    private static String utilTime(int value) {
        if (value < 10) return "0" + String.valueOf(value);
        else return String.valueOf(value);
    }

    private void setAlarms(int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(mContext, RC_ALARM, alarmIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5 * 60 * 1000, alarmPendingIntent);
        Toast.makeText(mContext, "Alarms set", Toast.LENGTH_SHORT).show();
    }

    private void updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
        if (flag == -1) {
            view_start.setText(aTime);
            view_end.setText(aTime);
        } else if (flag == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            String t = String.valueOf(hours) + ":" + String.valueOf(minutes);
            Log.d("time", t);
            try {
                start_date = sdf.parse(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            view_start.setText(aTime);

        } else if (flag == 1) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            String t = String.valueOf(hours) + ":" + String.valueOf(minutes);
            Log.d("time", t);
            try {
                end_date = sdf.parse(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (start_date.before(end_date)) {
                Toast.makeText(getActivity(), "Correct Time Selected.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Start Time is Greater then End Time.", Toast.LENGTH_SHORT).show();
                view_start.setText("");
                view_end.setText("");
            }
            view_end.setText(aTime);
        }
    }
}


