package io.github.httpsphoenix30.mcproject.receivers;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import io.github.httpsphoenix30.mcproject.R;
import io.github.httpsphoenix30.mcproject.Timer_2015095;

public class AlarmReceiver extends BroadcastReceiver {

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if (calendar.get(Calendar.HOUR_OF_DAY) >= sharedPreferences.getInt(Timer_2015095.END_TIME_HR, 0)) {
            if (calendar.get(Calendar.MINUTE) >= sharedPreferences.getInt(Timer_2015095.END_TIME_MIN, 0)) {
                cancelAlarms(context);
                return;
            }
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            setAirquality(location.getLatitude(), location.getLongitude(), context);
        }
    }

    private void cancelAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, Timer_2015095.RC_ALARM, alarmIntent, 0);
        alarmManager.cancel(alarmPendingIntent);
    }

    private void showNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MC Project")
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION));

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }

    private void setAirquality(double latitude, double longitude, final Context context) {
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
                    String message = msg.getString("outside");
                    final String healthmsg = msg.getString("health");

                    final String airquality = String.valueOf(jsonRootObject1.getInt("country_aqi"));
                    final String descrip = jsonRootObject1.getString("country_description");
                    final String pollu = jsonRootObject1.getString("dominant_pollutant_canonical_name");

                    Log.e(TAG, "data" + airquality + descrip + pollu);
                    int aqi = jsonRootObject1.getInt("country_aqi");
                    if (aqi < 100) {
                        showNotification(context, "Weather fine", message);
                    } else {
                        showNotification(context, "Bad weather", message);
                    }


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

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
