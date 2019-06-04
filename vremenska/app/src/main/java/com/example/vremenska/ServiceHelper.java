package com.example.vremenska;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.mbms.StreamingServiceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ServiceHelper extends Service {

    private static final String LOG_TAG = "ServiceHelper";
    private static final long PERIOD = 5000L;

    private RunnableExample mRunnable;

    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String KEY = "&APPID=21ea253e17b57b6663d7867a95b1452a&units=metric";
    public String GET_INFO;
    public HTTPHelper httpHelper;
    public String location, temperature;
    public String GET_WEATHER;
    private DBHelper weather_helper;
    private Calendar time;
    private String dan;
    private String data_time;


    @Override
    public void onCreate() {
        super.onCreate();

        httpHelper = new HTTPHelper();

        weather_helper = new DBHelper(this);
        mRunnable = new RunnableExample();
        mRunnable.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRunnable.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = new LocalBinder();
        location = intent.getStringExtra("location");
        return binder;
    }


    public class LocalBinder extends Binder{
        ServiceHelper getService(){
            return ServiceHelper.this;
        }
    }

    private class RunnableExample implements Runnable {
        private Handler mHandler;
        private boolean mRun = false;

        public RunnableExample() {
            mHandler = new Handler(getMainLooper());
        }

        public void start() {
            mRun = true;
            mHandler.postDelayed(this, PERIOD);
        }

        public void stop() {
            mRun = false;
            mHandler.removeCallbacks(this);
        }

        @Override
        public void run() {
            if (!mRun) {
                return;
            }

            GET_WEATHER = BASE_URL + location + KEY;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonobject = httpHelper.getJSONObjectFromURL(GET_WEATHER);
                        JSONObject mainobject = jsonobject.getJSONObject("main");

                        final String tmp = mainobject.get("temp").toString();
                        final String pressure = mainobject.get("pressure").toString();
                        final String humidity = mainobject.get("humidity").toString();


                        JSONObject jsonobject1 = httpHelper.getJSONObjectFromURL(GET_WEATHER);
                        JSONObject sysobject = jsonobject.getJSONObject("sys");

                        long sun = Long.valueOf(sysobject.get("sunrise").toString()) * 1000;
                        Date date1 = new Date(sun);

                        final String sunrise = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date1);

                        long night = Long.valueOf(sysobject.get("sunset").toString()) * 1000;
                        Date date2 = new Date(night);
                        final String sunset = new SimpleDateFormat("hh:mma", Locale.ENGLISH).format(date2);

                        JSONObject jsonobject2 = httpHelper.getJSONObjectFromURL(GET_INFO);
                        JSONObject windobject = jsonobject2.getJSONObject("wind");

                        final String wind_speed = windobject.get("speed").toString();

                        double degree = windobject.getDouble("deg");
                        final String wind_direction = windConverter(degree);

                        time = Calendar.getInstance();
                        int dan;
                        String day;
                        dan = time.get(Calendar.DAY_OF_WEEK);
                        switch (dan) {
                            case Calendar.MONDAY:
                                day = "Ponedeljak";
                                break;
                            case Calendar.TUESDAY:
                                day = "Utorak";
                                break;
                            case Calendar.WEDNESDAY:
                                day = "Sreda";
                                break;
                            case Calendar.THURSDAY:
                                day = "Cetvrtak";
                                break;
                            case Calendar.FRIDAY:
                                day = "Petak";
                                break;
                            case Calendar.SATURDAY:
                                day = "Subota";
                                break;
                            case Calendar.SUNDAY:
                                day = "Nedelja";
                                break;
                            default:
                                day = "";
                        }

                        SimpleDateFormat data_1 = new SimpleDateFormat("dd.MM.yyyy.");
                        data_time = data_1.format(time.getTime());

                        WeatherData data = new WeatherData(day, data_time, location, temperature, pressure, humidity, sunset, sunrise, wind_speed, wind_direction);
                        weather_helper.insert(data);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


            WeatherData read = weather_helper.readData(location);

            NotificationCompat.Builder b = new NotificationCompat.Builder(ServiceHelper.this);
            b.setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.sun)
                    .setTicker("Vremenska prognoza")
                    .setContentTitle("Temperatura je azurirana ")
                    .setContentText(read.getTemperature() + "°C")
                    .setContentInfo("INFO");

            NotificationManager manager = (NotificationManager) ServiceHelper.this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(1, b.build());

            mHandler.postDelayed(this, PERIOD);
        }
    }

    public String windConverter(double degree){
        if(degree <= 22.5 && degree > 337.5){
            return "Sever";
        }
        if(degree > 22.5 && degree <= 67.5){
            return "Severo-istok";
        }
        if(degree > 67.5 && degree <= 112.5){
            return "Istok";
        }
        if(degree > 112.5 && degree <= 157.5){
            return "Jugo-istok";
        }
        if(degree > 157.5 && degree <= 202.5){
            return "Jug";
        }
        if(degree > 202.5 && degree <= 247.5){
            return "Jugo-zapad";
        }
        if(degree > 247.525 && degree <= 292.5){
            return "Zapad";
        }
        return "Severo-zapad";

    }
}
