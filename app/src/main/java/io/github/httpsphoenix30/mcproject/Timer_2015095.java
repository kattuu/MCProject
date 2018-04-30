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
import java.util.Calendar;
import java.util.List;

/**
 * Created by vishal on 28/04/18.
 */

public class Timer_2015095 extends Fragment {
    static final int TIME_DIALOG_ID = 1111;
    private TextView view_start;
    private TextView view_end;
    public Button btnClick_start;
    public Button btnClick_end;
    private int flag = -1;
    private int hr;
    //private int hr_end;
    private int min;
    //private int min_end;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {

        View frag_view = inflater.inflate(R.layout.fragment_timer_2015095, viewGroup, false);

        view_start = (TextView) frag_view.findViewById(R.id.output_start);
        view_end = (TextView) frag_view.findViewById(R.id.output_end);
        final Calendar c = Calendar.getInstance();
        hr = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        updateTime(hr, min);
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
    }

    protected Dialog createdDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(getActivity(), timePickerListener, hr, min, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
// TODO Auto-generated method stub
            hr = hourOfDay;
            min = minutes;
            updateTime(hr, min);
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
            view_start.setText(aTime);
        }
        else if(flag == 1) {
            view_end.setText(aTime);
        }
    }
}


