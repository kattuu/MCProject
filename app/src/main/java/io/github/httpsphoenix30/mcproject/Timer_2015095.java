package io.github.httpsphoenix30.mcproject;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;


import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by vishal on 28/04/18.
 */

public class Timer_2015095 extends Fragment {
    static final int TIME_DIALOG_ID_START = 1111;
    static final int TIME_DIALOG_ID_END = 2222;
    private TextView view_start;
    private TextView view_end;
    public Button btnClick_start;
    public Button btnClick_end;
    private int flag = -1;
    private int hr_start;
    private int hr_end;
    private int min_start;
    private int min_end;
    private Date start_date;
    private Date end_date;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {

        View frag_view = inflater.inflate(R.layout.fragment_timer_2015095, viewGroup, false);

        view_start = (TextView) frag_view.findViewById(R.id.output_start);
        view_end = (TextView) frag_view.findViewById(R.id.output_end);
        //final Calendar c = Calendar.getInstance();
//        hr = c.get(Calendar.HOUR_OF_DAY);
//        min = c.get(Calendar.MINUTE);
        //updateTime(hr, min);
        addButtonClickListener(frag_view);

        return frag_view;
    }

    public void addButtonClickListener(View view) {
        btnClick_start = (Button) view.findViewById(R.id.btnClick_start);
        btnClick_end = (Button) view.findViewById(R.id.btnClick_end);
        btnClick_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                createdDialog(TIME_DIALOG_ID_START).show();
            }
        });
        btnClick_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                createdDialog(TIME_DIALOG_ID_END).show();
            }
        });
    }

    protected Dialog createdDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID_START:
                return new TimePickerDialog(getActivity(), timePickerListener, hr_start, min_start, true);
            case TIME_DIALOG_ID_END:
                return new TimePickerDialog(getActivity(), timePickerListener, hr_end, min_end, true);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
// TODO Auto-generated method stub
            if(flag == 0) {
                hr_start = hourOfDay;
                min_start = minutes;

                updateTime(hr_start, min_start);
            }
            else if(flag == 1) {
                hr_end = hourOfDay;
                min_end = minutes;

                updateTime(hr_end, min_end);
            }

        }
    };

    private static String utilTime(int value) {
        if (value < 10) return "0" + String.valueOf(value);
        else return String.valueOf(value);
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
        if(flag == -1) {
            view_start.setText(aTime);
            view_end.setText(aTime);
        }
        else if(flag == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            String t = String.valueOf(hours) + ":" + String.valueOf(minutes);
            Log.d("time", t);
            try {
                start_date = sdf.parse(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            view_start.setText(aTime);
        }
        else if(flag == 1) {

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            String t = String.valueOf(hours) + ":" + String.valueOf(minutes);
            Log.d("time", t);
            try {
                end_date = sdf.parse(t);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(start_date.before(end_date)) {
                Toast.makeText(getActivity(),"Correct Time Selected.",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(),"Start Time is Greater then End Time.",Toast.LENGTH_SHORT).show();
                view_start.setText("");
                view_end.setText("");
            }
            view_end.setText(aTime);
        }
    }
}


