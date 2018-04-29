package io.github.httpsphoenix30.mcproject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;

/**
 * Created by vishal on 28/04/18.
 */

public class Timer_2015095 extends Fragment implements RangeTimePickerDialog.ISelectedTime {
    private static final int MY_FRAGMENT_ID = 100;
    private Button clock;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){

        View view =inflater.inflate(R.layout.fragment_timer_2015095,viewGroup,false);

        clock =view.findViewById(R.id.clock);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialogTimePicker();
            }
        });

        return view;
    }

    public void showCustomDialogTimePicker()
    {
        // Create an instance of the dialog fragment and show it
        RangeTimePickerDialog dialog = new RangeTimePickerDialog();
        dialog.newInstance();
        dialog.setIs24HourView(false);
        dialog.setRadiusDialog(20);
        dialog.setTextTabStart("Start");
        dialog.setTextTabEnd("End");
        dialog.setTextBtnPositive("Accept");
        dialog.setTextBtnNegative("Close");
        dialog.setValidateRange(false);
        dialog.setColorBackgroundHeader(R.color.colorPrimary);
        dialog.setColorBackgroundTimePickerHeader(R.color.colorPrimary);
        dialog.setColorTextButton(R.color.colorPrimaryDark);
        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        dialog.show(fragmentManager, "");
    }



    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd)
    {

        Toast.makeText(getActivity(), "Start: "+hourStart+":"+minuteStart+"\nEnd: "+hourEnd+":"+minuteEnd, Toast.LENGTH_SHORT).show();
    }


}

