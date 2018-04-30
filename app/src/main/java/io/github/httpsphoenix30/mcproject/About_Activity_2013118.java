package io.github.httpsphoenix30.mcproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class About_Activity_2013118 extends AppCompatActivity {

    ImageView imageView, image1,image2,image3,image4,image5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__2013118);
        imageView =findViewById(R.id.imageView);
        image1 =findViewById(R.id.image1);
        image2 =findViewById(R.id.image2);
        image3 =findViewById(R.id.image3);
        image4 =findViewById(R.id.image4);
        image5 =findViewById(R.id.image5);

        Picasso.with(this)
                .load(R.drawable.wave)
                .into(imageView);
        Picasso.with(this)
                .load(R.drawable.pp)
                .into(image1);
        Picasso.with(this)
                .load(R.drawable.pp2)
                .into(image2);
        Picasso.with(this)
                .load(R.drawable.pp3)
                .into(image3);
        Picasso.with(this)
                .load(R.drawable.pp5)
                .into(image4);
        Picasso.with(this)
                .load(R.drawable.pp4)
                .into(image5);
    }
}
