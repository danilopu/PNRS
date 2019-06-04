package com.example.vremenska;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONObject;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "weather1.db";
    public static final int DATABASE_VERSION = 1;


    public static final String COLUMN_DAY = "day";
    public static final String TABLE_NAME = "weather";
    public static final String COLUMN_DATE_TIME = "date_time";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_PRESSURE = "pressuere";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_SUN_SET = "sun_set";
    public static final String COLUMN_SUN_RISE = "sun_rise";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_WIND_DIRECTION = "wind_direction";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MWSG", "CREATE");
        final String SQL_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_DAY + " TEXT," +
                COLUMN_DATE_TIME + " TEXT," +
                COLUMN_LOCATION + " TEXT," +
                COLUMN_TEMPERATURE + " TEXT," +
                COLUMN_PRESSURE + " TEXT," +
                COLUMN_HUMIDITY + " TEXT," +
                COLUMN_SUN_SET + " TEXT," +
                COLUMN_SUN_RISE + " TEXT," +
                COLUMN_WIND_SPEED + " TEXT," +
                COLUMN_WIND_DIRECTION + " TEXT" +
                ");";

        db.execSQL(SQL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(WeatherData data) {

        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("prvi", "prvi");
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, data.getDay());
        Log.d("prvi", "drugi");
        values.put(COLUMN_DATE_TIME, data.getDate_time());
        Log.d("prvi", "treci");
        values.put(COLUMN_LOCATION, data.getLocation());
        Log.d("prvi", "cetvrti");
        values.put(COLUMN_TEMPERATURE, data.getTemperature());
        Log.d("prvi", "peti");
        values.put(COLUMN_PRESSURE, data.getPressure());
        Log.d("prvi", "sesti");
        values.put(COLUMN_HUMIDITY, data.getHumidity());
        Log.d("prvi", "sedmi");
        values.put(COLUMN_SUN_SET, data.getSun_set());
        Log.d("prvi", "osmi");
        values.put(COLUMN_SUN_RISE, data.getSun_rise());
        Log.d("prvi", "devet");
        values.put(COLUMN_WIND_SPEED, data.getWind_speed());
        Log.d("prvi", "deset");
        values.put(COLUMN_WIND_DIRECTION, data.getWind_direction());
        Log.d("prvi", "JEDANAEST");
        WeatherData d = exists(data.getLocation(),data.getDate_time());

        if(d == null){
            db.insert(TABLE_NAME, null, values);
            close();
        }else{
            close();
        }

    }

    public WeatherData exists(String location, String date_time){
        SQLiteDatabase db = this.getReadableDatabase();
        WeatherData data;
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_LOCATION + " = \"" + location + "\" AND "
                + COLUMN_DATE_TIME + " = \"" + date_time + "\" ;", null, null);



        if (cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();
        data = createData(cursor);


        return data;


    }

    public void insert_location(WeatherData data) {
        Log.d("MESG", "INSERT LOCATION");
        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, data.getDay());
        values.put(COLUMN_DATE_TIME, data.getDate_time());
        values.put(COLUMN_LOCATION, data.getLocation());
        values.put(COLUMN_TEMPERATURE, data.getTemperature());
        values.put(COLUMN_PRESSURE, data.getPressure());
        values.put(COLUMN_HUMIDITY, data.getHumidity());
        values.put(COLUMN_SUN_SET, data.getSun_set());
        values.put(COLUMN_SUN_RISE, data.getSun_rise());
        values.put(COLUMN_WIND_SPEED, data.getWind_speed());
        values.put(COLUMN_WIND_DIRECTION, data.getWind_direction());

        WeatherData d = exists_location(data.getLocation());

        if(d == null){
            db.insert(TABLE_NAME, null, values);
            close();
        }else{
            close();
        }


    }


    public WeatherData exists_location(String location){
        SQLiteDatabase db = this.getReadableDatabase();
        WeatherData data;
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_LOCATION + "=?",
                new String[] {location}, null, null, null);

        if (cursor.getCount() <= 0) {
            return null;
        }

        cursor.moveToFirst();
        data = createData(cursor);

        Log.d("MESG", String.valueOf((cursor.getCount())));


        return data;


    }



    public WeatherData[] read_all_data(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null, null);

        if(cursor.getCount() <= 0){
            return null;
        }

        WeatherData[] all_data = new WeatherData[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            all_data[i++] = createData(cursor);
        }

        close();
        return all_data;

    }

    public WeatherData readData(String location){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_LOCATION + "=?",
                new String[] {location}, null, null, null);
        cursor.moveToLast();
        WeatherData data = createData(cursor);

        close();
        return data;
    }


    public WeatherData readDay(String day){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_DAY + "=?",
                new String[] {day}, null, null, null);
        cursor.moveToFirst();
        WeatherData data = createData(cursor);

        close();
        return data;
    }

    public WeatherData[] read_data_location(String location){
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_LOCATION + "=?",
                new String[] {location}, null, null, null);

        if(cursor.getCount() <= 0){
            return null;
        }


        WeatherData[] all_data = new WeatherData[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            all_data[i++] = createData(cursor);
        }

        close();
        return all_data;


    }

    public void deleteData(String date_time, String location){
        Log.d("DELETE", "DELETE_PRE");
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE "
                + COLUMN_LOCATION + " = \"" + location + "\" AND "
                + COLUMN_DATE_TIME + " = \"" + date_time + "\" ;", null, null);

        if(cursor.getCount() > 0){
            db.delete(TABLE_NAME, COLUMN_DATE_TIME + "=? and " + COLUMN_LOCATION , new String[] { date_time, location});
        }

        Log.d("DELETE", "DELETE_POSLE");

    }


    public WeatherData[] sort_date_time(String location){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_DATE_TIME+" DESC");

        WeatherData[] all_data = new WeatherData[cursor.getCount()];
        int i = 0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            all_data[i++] = createData(cursor);
        }



        close();
        return all_data;

    }



    private WeatherData createData(Cursor cursor){
        String day = cursor.getString(cursor.getColumnIndex(COLUMN_DAY));
        String date_time = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME));
        String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
        String temperature = cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE));
        String pressure = cursor.getString(cursor.getColumnIndex(COLUMN_PRESSURE));
        String humidity = cursor.getString(cursor.getColumnIndex(COLUMN_HUMIDITY));
        String sun_set = cursor.getString(cursor.getColumnIndex(COLUMN_SUN_SET));
        String sun_rise = cursor.getString(cursor.getColumnIndex(COLUMN_SUN_RISE));
        String wind_speed = cursor.getString(cursor.getColumnIndex(COLUMN_WIND_SPEED));
        String wind_direction = cursor.getString(cursor.getColumnIndex(COLUMN_WIND_DIRECTION));

        return new WeatherData(day, date_time, location, temperature, pressure, humidity, sun_set, sun_rise, wind_speed, wind_direction);
    }


    public Cursor getCity(String city){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOCATION + " = \"" + city +"\";"
                ,null,null);
        return cursor;
    }
}
