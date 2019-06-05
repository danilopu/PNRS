package com.example.vremenska;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    private String date_time;
    private String location;
    private String dan;
    private TextView Location;
    private DBHelper weatherhelper;
    private TextView tPon,tUto,tSre,tCet,tPet,tSub,tNed,tMin,tMax,tIzn,tIsp;
    private ImageButton ibHlad,ibTopl;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("nesto bezveze", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Location = findViewById(R.id.LocationStatistics);
        Bundle bundleLocation = getIntent().getExtras();
        Location.setText(bundleLocation.get("edit1").toString());
        location = bundleLocation.get("edit1").toString();

        Bundle bundle1 = getIntent().getExtras();
        dan = bundle1.get("edit2").toString();

        Bundle bundle2 = getIntent().getExtras();
        date_time = bundle2.get("edit3").toString();

        weatherhelper = new DBHelper(this);

        tPon = (TextView) findViewById(R.id.pon);
        tUto = (TextView) findViewById(R.id.uto);
        tSre = (TextView) findViewById(R.id.sre);
        tCet = (TextView) findViewById(R.id.cet);
        tPet = (TextView) findViewById(R.id.pet);
        tSub = (TextView) findViewById(R.id.sub);
        tNed = (TextView) findViewById(R.id.ned);
        tMin=(TextView)findViewById(R.id.add_min_temp);
        tMax=(TextView)findViewById(R.id.add_max_temp);
        tIzn=(TextView)findViewById(R.id.listMax);
        tIsp=(TextView)findViewById(R.id.listMin);

        tIzn.setVisibility(View.INVISIBLE);
        tIsp.setVisibility(View.INVISIBLE);
        ibHlad=(ImageButton)findViewById(R.id.snow_stat);
        ibTopl=(ImageButton)findViewById(R.id.sun_stat);

        ibHlad.setOnClickListener(this);
        ibTopl.setOnClickListener(this);


        Calendar time=Calendar.getInstance();
        SimpleDateFormat data=new SimpleDateFormat("dd.mm.yyyy");
        date_time = data.format(time.getTime());

        cursor = weatherhelper.getCity(location);//vraca mi cursor svi dana za gradove
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String date=cursor.getString(1);;
            int value=getDayoFWeek(date);
            String day=dayOfWeek(value);
            //Log.d("dan",day);
            put(day,cursor);
        }

        boldTV();

        min(cursor);
        max(cursor);


    }

    public int getDayoFWeek(String date){

        int dayOfWeek;
        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        try {
            Date d = format.parse(date);
            Calendar c = Calendar.getInstance();
            c.setTime(d); // yourdate is an object of type Date

            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            Log.d("dan",String.valueOf(dayOfWeek));
            System.out.println(date);
            return dayOfWeek;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String dayOfWeek(int d){
        if(d==2){
            return getString(R.string.ned);
        }else if(d==3){
            return getString(R.string.pon);
        }else if(d==4){
            return getString(R.string.uto);
        }else if(d==5){
            return getString(R.string.sre);
        }else if(d==6){
            return getString(R.string.cet);
        }else if(d==7){
            return getString(R.string.pet);
        }else
            return getString(R.string.sub);
    }
    public void put(String day,Cursor cursor){
        if(day.equals(getString(R.string.pon))){
            tPon.setText(getString(R.string.pon)+"                 "+cursor.getDouble(3)+"            "+cursor.getDouble(4)+"                       "+cursor.getDouble(5));
        }else if(day.equals(getString(R.string.uto))){
            Log.d("mesage", "UTORAKJEBENI");
            tUto.setText(getString(R.string.uto)+"                        "+cursor.getDouble(3)+"             "+cursor.getDouble(4)+"                       "+cursor.getDouble(5));
        }else if(day.equals(getString(R.string.sre))){
            Log.d("mesage", "UTORAKJEBENI");
            tSre.setText(getString(R.string.sre)+"                         "+cursor.getDouble(3)+"             "+cursor.getDouble(4)+"                       "+cursor.getDouble(5));
        }else if(day.equals(getString(R.string.cet))){
            tCet.setText(getString(R.string.cet)+"                     "+cursor.getDouble(3)+"             "+cursor.getDouble(4)+"                       "+cursor.getDouble(5));
        }else if(day.equals(getString(R.string.pet))){
            tPet.setText(getString(R.string.pet)+"                          "+cursor.getDouble(3)+"             "+cursor.getDouble(4)+"                       "+cursor.getDouble(5));
        }else if(day.equals(getString(R.string.sub))){
            tSub.setText(getString(R.string.sub)+"                        "+cursor.getDouble(3)+"             "+cursor.getDouble(4)+"                       "+cursor.getDouble(5));
        }else{
            tNed.setText(getString(R.string.ned)+"                       "+cursor.getDouble(3)+"             "+cursor.getDouble(4)+"                       "+cursor.getDouble(5));
        }
    }

    public void min(Cursor cursor){
        double min=1000;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getDouble(3)<min){
                min=cursor.getDouble(3);
            }
        }
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getDouble(3)==min){
                String date=cursor.getString(1);;
                int value=getDayoFWeek(date);
                String day=dayOfWeek(value);
                String temp=String.valueOf(cursor.getDouble(3));
                tMin.append(day +" "+ temp+"\n");
            }
        }

    }

    public void max(Cursor cursor){
        double max=10;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getDouble(3)>max){
                max=cursor.getDouble(3);
            }
        }
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if(cursor.getDouble(3)==max){
                String date=cursor.getString(1);;
                int value=getDayoFWeek(date);
                String day=dayOfWeek(value);
                tMax.append(day + " "+ String.valueOf(cursor.getDouble(3))+"\n");
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.snow_stat:
                tIzn.setText("");
                tIzn.setVisibility(View.INVISIBLE);
                tIsp.setVisibility(View.VISIBLE);
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    if (cursor.getDouble(3) < 10) {
                        String date = cursor.getString(1);
                        ;
                        int value = getDayoFWeek(date);
                        String day = dayOfWeek(value);
                        tIsp.append(day + " " + String.valueOf(cursor.getDouble(3)) + "\n");
                    }
                }
                break;
            case R.id.sun_stat:
                tIsp.setText("");
                tIsp.setVisibility(View.INVISIBLE);
                tIzn.setVisibility(View.VISIBLE);
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    if (cursor.getDouble(3) > 10) {
                        String date = cursor.getString(1);
                        ;
                        int value = getDayoFWeek(date);
                        String day = dayOfWeek(value);
                        tIzn.append(day + " " + String.valueOf(cursor.getDouble(3)) + "\n");
                    }
                }
                break;
        }
    }

    public void boldTV(){
        /*Calendar time=Calendar.getInstance();
        SimpleDateFormat data=new SimpleDateFormat("dd.mm.yyyy");
        String data_time=data.format(time.getTime());
        int value=getDayoFWeek(data_time);*/
        String day=dan;
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);


        if(day.equals(getString(R.string.pon))){
            tPon.setTypeface(boldTypeface);
        }else if(day.equals(getString(R.string.uto))){
            tUto.setTypeface(boldTypeface);
        }else if(day.equals(getString(R.string.sre))){
            tSre.setTypeface(boldTypeface);
        }else if(day.equals(getString(R.string.cet))){
            tCet.setTypeface(boldTypeface);
        }else if(day.equals(getString(R.string.pet))){
            tPet.setTypeface(boldTypeface);
        }else if(day.equals(getString(R.string.sub))){
            tSub.setTypeface(boldTypeface);
        }else{
            tNed.setTypeface(boldTypeface);
        }

    }

}
