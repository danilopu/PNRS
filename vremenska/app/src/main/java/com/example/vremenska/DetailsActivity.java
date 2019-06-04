package com.example.vremenska;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.RunnableFuture;

public class DetailsActivity extends AppCompatActivity implements  View.OnClickListener {
    private Spinner dropdown;
    private String dan;
    Calendar time;
    private String temp1, temp2, temp3, sun1, sun2, wind1, wind2;
    private LinearLayout templayout , sunlayout , windlayout;
    private Button temperatura, sunce , vetar, statistics;
    private ImageButton refresh;
    private TextView tmp1 , tmp2, tmp3, sunRise, sunSet, windDir, windSpeed;
    private TextView kojigrad , kojidan, tLup , updatedday;
    String [] SPINNERLIST = {"°C", "F"};




    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String KEY = "&APPID=427edf80de13c683dda62f905bed47fc&units=metric";
    public String GET_INFO;
    private HTTPHelper httpHelper;
    private DBHelper weather_helper;
    private WeatherData weatherData; //object for last data in case it already exists
    String grad, data_time;

    private ArrayAdapter<String> arrayAdapter;

    private Button stop_service;
    private ServiceHelper mService;
    boolean mBound = false;

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("START SERVIS", "START SERVIS");
        Intent intent = new Intent(this, ServiceHelper.class);
        intent.putExtra("location", grad);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mBound == true)
            unbindService(connection);
        mBound = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        weather_helper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);




        dropdown = (Spinner) findViewById(R.id.dropdownmenu);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        dropdown.setAdapter(arrayAdapter);




        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        temperatura = findViewById(R.id.temperatura);
        temperatura.setOnClickListener(this);

        statistics = findViewById(R.id.statistics);
        statistics.setOnClickListener(this);

        sunce = findViewById(R.id.sunce);
        sunce.setOnClickListener(this);

        vetar = findViewById(R.id.vetar);
        vetar.setOnClickListener(this);

        kojigrad = findViewById(R.id.kojigrad);
        //kojigrad.setOnClickListener(this);

        kojidan = findViewById(R.id.kojidan);
        //kojidan.setOnClickListener(this);

        updatedday = findViewById(R.id.day);


        kojigrad = (TextView) findViewById(R.id.kojigrad);
        Bundle bundle = getIntent().getExtras();
        grad = bundle.getString("grad");


        if (bundle!= null){

            kojigrad =(TextView)findViewById(R.id.kojigrad);
            kojigrad.setText(bundle.get("grad").toString());
        }

        stop_service = findViewById(R.id.stop_service);
        stop_service.setOnClickListener(this);

        WeatherData data1 = new WeatherData("Ponedeljak","17.05.2019.", "Novi Sad", "22", "1024", "56", "12:13", "13:12", "65", "Sever");
        Log.d("data1", data1.getLocation());
        weather_helper.insert(data1);

        WeatherData data2 = new WeatherData("Utorak","17.05.2019.", "Novi Sad", "21", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data2);
        WeatherData data3 = new WeatherData("Sreda","18.05.2019.", "Novi Sad", "24", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data3);
        WeatherData data4 = new WeatherData("Cetvrtak","19.05.2019.", "Novi Sad", "25", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data4);
        WeatherData data5 = new WeatherData("Petak","20.05.2019.", "Novi Sad", "18", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data5);
        WeatherData data6 = new WeatherData("Subota","21.05.2019.", "Novi Sad", "23", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data6);
        WeatherData data7 = new WeatherData("Nedelja","22.05.2019.", "Novi Sad", "20", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data7);


        WeatherData data11 = new WeatherData("Ponedeljak","17.05.2019.", "Paris", "22", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data11);
        WeatherData data22 = new WeatherData("Utorak","17.05.2019.", "Paris", "21", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data22);
        WeatherData data33 = new WeatherData("Sreda","18.05.2019.", "Paris", "24", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data33);
        WeatherData data44 = new WeatherData("Cetvrtak","19.05.2019.", "Paris", "25", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data44);
        WeatherData data55 = new WeatherData("Petak","20.05.2019.", "Paris", "18", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data55);
        WeatherData data66 = new WeatherData("Subota","21.05.2019.", "Paris", "23", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data66);
        WeatherData data77 = new WeatherData("Nedelja","22.05.2019.", "Paris", "20", "1024", "56", "12:13", "13:12", "65", "Sever");
        weather_helper.insert(data77);



       /* Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case 2:
                kojidan.setText(R.string.pon);
                break;
            case 3:
                kojidan.setText(R.string.uto);
                break;
            case 4:
                kojidan.setText(R.string.sre);
                break;
            case 5:
                kojidan.setText(R.string.cet);
                break;
            case 6:
                kojidan.setText(R.string.pet);
                break;
            case 7:
                kojidan.setText(R.string.sub);
                break;
            case 1:
                kojidan.setText(R.string.ned);
        }
        */
        kojidan = findViewById(R.id.kojidan);
        time = Calendar.getInstance();
        String[] days = new String[] {"Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"};
        kojidan.setText("Dan: " + days[time.get(Calendar.DAY_OF_WEEK) - 2]);

        httpHelper = new HTTPHelper();
        GET_INFO = BASE_URL + bundle.get("grad").toString() + KEY;
        Log.d("URL", GET_INFO);
        tmp1 = findViewById(R.id.sunTempText);
        tmp2 = findViewById(R.id.sunPresText);
        tmp3 = findViewById(R.id.sunHumText);
        sunRise = findViewById(R.id.sunriseText);
        sunSet = findViewById(R.id.sunsetText);
        windDir = findViewById(R.id.windDirText);
        windSpeed = findViewById(R.id.windSpeText);

        templayout = (LinearLayout)findViewById(R.id.tempLayout);
        sunlayout = (LinearLayout)findViewById(R.id.sunriseLayout);
        windlayout = (LinearLayout)findViewById(R.id.windLayout);

        templayout.setVisibility(View.INVISIBLE);
        sunlayout.setVisibility(View.INVISIBLE);
        windlayout.setVisibility(View.INVISIBLE);

        SimpleDateFormat data_1 = new SimpleDateFormat("dd.MM.yyyy.");
        kojidan.setText(data_1.format(time.getTime()));
        data_time = data_1.format(time.getTime());


        /*new Thread (new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = httpHelper.getJSONObjectFromURL(GET_INFO);
                    final JSONObject temperature = jsonObject.getJSONObject("main");
                    JSONObject sun = jsonObject.getJSONObject("sys");
                    JSONObject wind = jsonObject.getJSONObject("wind");

                    final String temp = temperature.get("temp").toString();
                    temp1 ="Temperatura: " + temperature.get("temp").toString();
                    temp2 = "Pritisak: " +temperature.get("pressure").toString() + " mbar";
                    temp3 ="Vlaznost vazduha: " + temperature.get("humidity").toString() + " %";

                    long s1 = Long.valueOf(sun.get("sunrise").toString())*1000;
                    long s2 = Long.valueOf(sun.get("sunset").toString())*1000;
                    Date d1 = new java.util.Date(s1);
                    Date d2 = new java.util.Date(s2);
                   // Locale locale = new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build();
                    sun1 = "Izlazak sunca: " + new SimpleDateFormat("hh:mma ", Locale.ENGLISH).format(d1);
                    sun2 = "Zalazak sunca:" + new SimpleDateFormat("hh:mma ", Locale.ENGLISH).format(d2);

                    wind1 = "Brzina vetra: " + wind.get("speed").toString() + "m/s";
                    wind2 = "Pravac: " + windConverter(wind.getDouble("deg"));

                    weatherData = new WeatherData(dan, data_time, grad, temp, temp2, temp3, sun1, sun2, wind1, wind2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tmp1.setText(temp1);
                            tmp2.setText(temp2);
                            tmp3.setText(temp3);
                            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    switch (parent.getItemAtPosition(position).toString()){
                                        case "°C":
                                            tmp1.setText(temp1);
                                            break;
                                        default:
                                            double temperature = Double.parseDouble(temp)*9/5 + 32;
                                            tmp1.setText("Temperatura: " + Double.toString(temperature));
                                            break;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    tmp1.setText(temp1);
                                }
                            });

                            sunRise.setText(sun1);
                            sunSet.setText(sun2);
                            windSpeed.setText(wind1);
                            windDir.setText(wind2);
                            time = Calendar.getInstance();
                            String[] days = new String[] {"Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"};
                            String dan = days[time.get(Calendar.DAY_OF_WEEK) - 2];


                            WeatherData[] read_day = weather_helper.read_data_location(grad);

                            weather_helper.insert_location(weatherData);

                            WeatherData data_read = weather_helper.readData(grad);

                            updatedday.setText("last updated \n" + data_read.getDate_time());



                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonobject = httpHelper.getJSONObjectFromURL(GET_INFO);
                    JSONObject mainobject = jsonobject.getJSONObject("main");

                    final String temp = mainobject.get("temp").toString();
                    final String pressure = mainobject.get("pressure").toString();
                    final String humidity = mainobject.get("humidity").toString();

                    JSONObject jsonobject1 = httpHelper.getJSONObjectFromURL(GET_INFO);
                    JSONObject sysobject = jsonobject1.getJSONObject("sys");

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



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            double tempCelsius = Double.parseDouble(temp);
                            int tempRound = (int)tempCelsius;
                            final String temperature = Integer.toString(tempRound);
                            //Temperature.setText("Temperatura: " + temperature + " °C");
                            //Pressuere.setText("Pritisak: " + pressure+ " hPA");
                            //Humidity.setText("Vlažnost vazduha: " + humidity + " %");

                            dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String selected = parent.getItemAtPosition(position).toString();
                                    if (selected.equals("°C")) {

                                        //Temperature.setText("Temperatura: " + temperature + " °C\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                    } else {
                                        double tempFarenhite = Double.parseDouble(temp);
                                        tempFarenhite = tempFarenhite * (9/5) + 32;
                                        int tempRound = (int)tempFarenhite;
                                        String temperature = Integer.toString(tempRound);
                                        //Temperature.setText("Temperatura: " + temperature + " °F\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                        //Sun_rise.setText("Izlazak sunca: " + sunrise);
                                        //Sun_set.setText("Zalazak sunca: " + sunset);
                                        //Wind_speed.setText("Brzina vetra: " + wind_speed + " m/s");
                                        //Wind_dir.setText("Pravac: " + wind_direction);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    //Temperature.setText("Temperatura: " + temperature + " °C\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                }



                            });

                            time = Calendar.getInstance();
                            String[] days = new String[] {"Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"};
                            dan = days[time.get(Calendar.DAY_OF_WEEK) - 2];
                            weatherData = new WeatherData(dan, data_time, grad, temp, pressure, humidity, sunset, sunrise, wind_speed, wind_direction);


                            WeatherData[] read_day = weather_helper.read_data_location(grad);

                            weather_helper.insert_location(weatherData);

                            WeatherData data_read = weather_helper.readData(grad);

                            updatedday.setText("last updated \n" + data_read.getDate_time());


                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

    @Override
    public void onClick(View v) {
        final WeatherData read = weather_helper.readData(grad);
        switch(v.getId()){
            case R.id.temperatura:
                templayout.setVisibility(View.VISIBLE);
                sunlayout.setVisibility(View.INVISIBLE);
                windlayout.setVisibility(View.INVISIBLE);

                dropdown.setAdapter(arrayAdapter);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //Temperature.setText("Temperatura: " + temperature + " °C");
                        //Pressuere.setText("Pritisak: " + pressure+ " hPA");
                        //Humidity.setText("Vlažnost vazduha: " + humidity + " %");

                        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selected = parent.getItemAtPosition(position).toString();
                                if (selected.equals("°C")) {
                                    MyNDK ndk = new MyNDK();
                                    double res;
                                    res = Double.valueOf(read.getTemperature());
                                    int res1;
                                    res1 = ndk.conversion(Integer.valueOf((int) res), 0);
                                    tmp1.setText("Temperatura: " + res1 + " °C");
                                    tmp2.setText("Pritisak: " + read.getPressure() + " hPA");
                                    tmp3.setText("Vlažnost vazduha: " + read.getHumidity() + " %");
                                } else {

                                    MyNDK ndk = new MyNDK();
                                    double res;
                                    res = Double.valueOf(read.getTemperature());
                                    int res1;
                                    res1 = ndk.conversion(Integer.valueOf((int) res), 1);
                                    tmp1.setText("Temperatura: " + res1 + " F");
                                    tmp2.setText("Pritisak: " + read.getPressure() + " hPA");
                                    tmp3.setText("Vlažnost vazduha: " + read.getHumidity() + " %");
                                }
                                    /*double tempFarenhite = Double.parseDouble(temp);
                                    tempFarenhite = tempFarenhite * (9/5) + 32;
                                    int tempRound = (int)tempFarenhite;
                                    String temperature = Integer.toString(tempRound);*/
                                //Temperature.setText("Temperatura: " + temperature + " °F\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                //Sun_rise.setText("Izlazak sunca: " + sunrise);
                                //Sun_set.setText("Zalazak sunca: " + sunset);
                                //Wind_speed.setText("Brzina vetra: " + wind_speed + " m/s");
                                //Wind_dir.setText("Pravac: " + wind_direction)
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                //Temperature.setText("Temperatura: " + temperature + " °C\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                            }


                        });
                    }
                });




                break;
            case R.id.sunce:
                templayout.setVisibility(View.INVISIBLE);
                sunlayout.setVisibility(View.VISIBLE);
                windlayout.setVisibility(View.INVISIBLE);


                sunRise.setText("Izlazak sunca: " + read.getSun_rise());
                sunSet.setText("Zalazak sunca: " + read.getSun_set());

                break;
            case R.id.vetar:
                templayout.setVisibility(View.INVISIBLE);
                sunlayout.setVisibility(View.INVISIBLE);
                windlayout.setVisibility(View.VISIBLE);

                windSpeed.setText("Brzina vetra: " + read.getWind_speed() + " m/s");
                windDir.setText("Pravac: " + read.getWind_direction());


                break;
            case R.id.statistics:
                WeatherData day_send = weather_helper.readDay(dan);

                Intent intent = new Intent(this,StatisticsActivity.class);
                intent.putExtra("edit1", grad);
                intent.putExtra("edit2", (day_send.getDay()));
                intent.putExtra("edit3", (day_send.getDate_time()));
                this.startActivity(intent);

                break;
            case R.id.refresh:
                refresh.setVisibility(View.INVISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonobject = httpHelper.getJSONObjectFromURL(GET_INFO);
                            JSONObject mainobject = jsonobject.getJSONObject("main");

                            final String temp = mainobject.get("temp").toString();
                            final String pressure = mainobject.get("pressure").toString();
                            final String humidity = mainobject.get("humidity").toString();

                            JSONObject jsonobject1 = httpHelper.getJSONObjectFromURL(GET_INFO);
                            JSONObject sysobject = jsonobject1.getJSONObject("sys");

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



                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    tmp1.setText("Temperatura: " + temp + " °C");
                                    tmp2.setText("Pritisak: " + pressure+ " hPA");
                                    tmp3.setText("Vlažnost vazduha: " + humidity + " %");
                                    sunRise.setText("Izlazak sunca: " + sunrise);
                                    sunSet.setText("Zalazak sunca: " + sunset);
                                    windSpeed.setText("Brzina vetra: " + wind_speed + " m/s");
                                    windDir.setText("Pravac: " + wind_direction);

                                    dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String selected = parent.getItemAtPosition(position).toString();
                                            if (selected.equals("°C")) {
                                                MyNDK ndk = new MyNDK();
                                                double res;
                                                res = Double.valueOf(read.getTemperature());
                                                int res1;
                                                res1 = ndk.conversion(Integer.valueOf((int) res), 0);
                                                tmp1.setText("Temperatura: " + res1 + " °C\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                            } else {
                                                MyNDK ndk = new MyNDK();
                                                double res;
                                                res = Double.valueOf(read.getTemperature());
                                                int res1;
                                                res1 = ndk.conversion(Integer.valueOf((int) res), 1);
                                                tmp1.setText("Temperatura: " + res1 + " °F\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                                sunRise.setText("Izlazak sunca: " + sunrise);
                                                sunSet.setText("Zalazak sunca: " + sunset);
                                                windSpeed.setText("Brzina vetra: " + wind_speed + " m/s");
                                                windDir.setText("Pravac: " + wind_direction);
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            //tmp1.setText("Temperatura: " + temp + " °C\nPritisak: " + pressure + " hPA" + "\nVlažnost vazduha: " + humidity + " %");
                                        }



                                    });


                                    time = Calendar.getInstance();
                                    String[] days = new String[] {"Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"};
                                    String dan = days[time.get(Calendar.DAY_OF_WEEK) - 2];

                                    weatherData = new WeatherData( dan, data_time, grad, temp, pressure, humidity, sunset, sunrise, wind_speed, wind_direction);
                                    weather_helper.insert(weatherData);

                                    WeatherData read_data = weather_helper.readData(grad);
                                    updatedday.setText("last updated \n" + read_data.getDate_time());

                                }
                            });




                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();




                break;

            case R.id.stop_service:
                Intent intent_service1 = new Intent(this, ServiceHelper.class);
                unbindService(connection);
                stopService(intent_service1);
                mBound = false;
                Log.d("STOP SERVIS", "STOP SERVIS");
                break;


        }

    }



    protected  String windConverter(double degree) {
        if (degree <= 22.5 && degree > 337.5) {
            return "Sever";
        }
        if (degree > 22.5 && degree <= 67.5) {
            return "Severo-istok";
        }
        if (degree > 67.5 && degree <= 112.5) {
            return "Istok";
        }
        if (degree > 112.5 && degree <= 157.5) {
            return "Jugo-istok";
        }
        if (degree > 157.5 && degree <= 202.5) {
            return "Jug";
        }
        if (degree > 202.5 && degree <= 247.5) {
            return "Jugo-zapad";
        }
        if (degree > 247.525 && degree <= 292.5) {
            return "Zapad";
        }
        return "Severo-zapad";
    }

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceHelper.LocalBinder binder = (ServiceHelper.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };
}


