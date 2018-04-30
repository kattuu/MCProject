package io.github.httpsphoenix30.mcproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vishal on 28/04/18.
 */


public class Home_2015095 extends Fragment {

    private TextView Airquality, Description, pollutant, Healthmsg;
    private TextView Temprature, Type, city;
    //TextView textwind;
    private String lat;
    private String lon;
    private double latitude;
    private double longitude;
    public static Context mContext;
    public static Handler uiHandler;
    private LocationManager locationManager;
    static final int REQUEST_LOCATION = 1;
    private CardView weatherCard;

    public Home_2015095(){

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {

        View view = inflater.inflate(R.layout.fragment_home_2015095, viewGroup, false);

        Temprature = (TextView) view.findViewById(R.id.temp_txt);
        Type = (TextView) view.findViewById(R.id.type);
        city = (TextView) view.findViewById(R.id.city);
        Airquality = view.findViewById(R.id.airquality);
        Description = view.findViewById(R.id.description);
        weatherCard = view.findViewById(R.id.top_card);
        pollutant = view.findViewById(R.id.pollutant);
        Healthmsg = view.findViewById(R.id.healthmsg);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation(null);
        getAirquality(null);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //weatherCard.setLayoutParams(new RelativeLayout.LayoutParams(displayMetrics.heightPixels,displayMetrics.widthPixels));
        Log.d("card", String.valueOf(displayMetrics.heightPixels));
        Log.d("card", String.valueOf(displayMetrics.widthPixels));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        uiHandler = new Handler();
    }

    void getLocation(PlaceModel placeModel) {
        if (placeModel != null) {
            setLocation(placeModel.getLat(), placeModel.getLon());
            return;
        }
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                setLocation(latitude, longitude);
                Log.d(String.valueOf(longitude), "show me");
            } else {
                Temprature.setText("Unable to find correct location.");
            }
        }
    }

    private void setLocation(double latitude, double longitude) {
        final String APPID = "4ea30f9df87b4dd999faede7533d9d7c";
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);

        final String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + APPID;

        new Thread(new Runnable() {

            public static final String TAG = "Mai";

            @Override
            public void run() {


                HttpURLConnection httpURLConnection = null;

                InputStream inputStream = null;
                try {
                    URL url = new URL(weatherUrl);

                    Log.e(TAG, "orf" + url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(10000);
                    inputStream = httpURLConnection.getInputStream();

                    JSONObject jsonRootObject = new JSONObject(convertStreamToString(inputStream));
                    JSONObject jsonObject = jsonRootObject.getJSONObject("coord");
                    final String lon = jsonObject.getString("lon");
                    final String lat = jsonObject.getString("lat");
                    JSONArray weathi = jsonRootObject.getJSONArray("weather");
                    final String description = weathi.getJSONObject(0).getString("main");
                    JSONObject main = jsonRootObject.getJSONObject("main");
                    final String temprature = String.valueOf(main.getInt("temp") - 273) + "°C";
                    final String humidity = String.valueOf(main.getInt("humidity"));
                    final String pressure = String.valueOf(main.getInt("pressure"));
                    final JSONObject wind = jsonRootObject.getJSONObject("wind");
                    final String windSpeed = String.valueOf(wind.getInt("speed"));
                    final String cityName = jsonRootObject.getString("name");


                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {


                            Type.setText(description);
                            Temprature.setText(temprature);
                            city.setText(cityName);


                        }


                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }


        }).start();
    }

    void getAirquality(PlaceModel placeModel) {
        if (placeModel != null) {
            setAirquality(placeModel.getLat(), placeModel.getLon());
            return;
        }
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                setAirquality(latitude, longitude);
                Log.d(String.valueOf(longitude), "show me");
            } else {
                Airquality.setText("Unable to find correct location.");
            }
        }
    }

    private void setAirquality(double latitude, double longitude) {
        final String APPID = "4ea30f9df87b4dd999faede7533d9d7c";
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);

        //https://api.breezometer.com/baqi/?lat=28.55&lon=77.27&key=231b9faae15c4907a275e5eef2470859  --breezometer Api


        final String keyId = "231b9faae15c4907a275e5eef2470859";
        final String airUrl = "https://api.breezometer.com/baqi/?lat=" + lat + "&lon=" + lon + "&key=" + keyId;

        //final String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + APPID;

        new Thread(new Runnable() {

            public static final String TAG = "Mai";

            @Override
            public void run() {



                HttpURLConnection httpURLConnection1 = null;

                InputStream inputStream1 = null;
                try {

                    URL url1 = new URL(airUrl);
                    httpURLConnection1 = (HttpURLConnection) url1.openConnection();
                    httpURLConnection1.setConnectTimeout(1000);
                    Log.e(TAG, "orf" + url1);

                    inputStream1 = httpURLConnection1.getInputStream();

                    JSONObject jsonRootObject1 = new JSONObject(convertStreamToString(inputStream1));



                    JSONObject msg = jsonRootObject1.getJSONObject("random_recommendations");
                    final String healthmsg = msg.getString("health");

                    final String airquality = String.valueOf(jsonRootObject1.getInt("country_aqi"));
                    final String descrip = jsonRootObject1.getString("country_description");
                    final String pollu = jsonRootObject1.getString("dominant_pollutant_canonical_name");

                    Log.e(TAG, "data" + airquality + descrip + pollu);


                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {


                            Airquality.setText(airquality);
                            Healthmsg.setText(healthmsg);
                            pollutant.setText(pollu);
                            Description.setText(descrip);

                        }


                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection1 != null) {
                        httpURLConnection1.disconnect();
                    }
                    if (inputStream1 != null) {
                        try {
                            inputStream1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation(null);
                break;
        }
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
