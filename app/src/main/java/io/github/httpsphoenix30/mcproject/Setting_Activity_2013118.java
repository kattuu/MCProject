package io.github.httpsphoenix30.mcproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;

public class Setting_Activity_2013118 extends AppCompatActivity implements View.OnClickListener{

    TextView unit, rating, about, feedback;
    Dialog d1, d2, d3;
    LinearLayout c;
    LinearLayout f;
    RippleBackground rippleBackground;
    TextView celcius;
    RatingBar ratingBar;
    Button submitRating, submitFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__2013118);
        unit=(TextView)findViewById(R.id.unit);
        rating=(TextView)findViewById(R.id.rating);
        about=(TextView)findViewById(R.id.about);
        feedback = (TextView)findViewById(R.id.feedback);
    }
    public void openUnitDialog(View view ) {
        d1 = new Dialog(Setting_Activity_2013118.this);
        d1.setContentView(R.layout.units_dialog);
        c=(LinearLayout)d1.findViewById(R.id.c);
        c.setOnClickListener(this);
        f=(LinearLayout)d1.findViewById(R.id.f);
        celcius =(TextView)d1.findViewById(R.id.ce);
        rippleBackground = (RippleBackground)d1.findViewById(R.id.celsiusText);
        f.setOnClickListener(this);
        d1.show();
    }
    public void openRatingDialog(View view) {
        d2 = new Dialog(Setting_Activity_2013118.this);
        d2.setContentView(R.layout.rating_dialog);
        ratingBar = (RatingBar)d2.findViewById(R.id.ratingBar);
        submitRating = (Button)d2.findViewById(R.id.submitRating);
        submitRating.setOnClickListener(this);
        d2.show();
    }
    public void openAboutActivity(View view) {
        Intent I = new Intent(getApplicationContext(), About_Activity_2013118.class);
        startActivity(I);
    }
    public void openFeedbackDialog(View view) {
        d3 = new Dialog(Setting_Activity_2013118.this);
        d3.setContentView(R.layout.feedback_dialog);
        submitFeedback = (Button)d3.findViewById(R.id.submitFeedback);
        submitFeedback.setOnClickListener(this);
        d3.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.unit:
                openUnitDialog(v);
                break;

            case R.id.c:
                rippleBackground.startRippleAnimation();
                d1.dismiss();
                break;
            case R.id.f:

                d1.dismiss();
                break;
            case R.id.rating:
                openRatingDialog(v);
                break;
            case R.id.about:
                openAboutActivity(v);
                break;
            case R.id.submitRating:
                Toast.makeText(getApplicationContext(), String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
                d2.dismiss();
                break;
            case R.id.feedback:
                openFeedbackDialog(v);
                break;
            case R.id.submitFeedback:
                d3.dismiss();
                break;


        }

    }
}

