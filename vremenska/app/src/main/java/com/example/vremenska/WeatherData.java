package com.example.vremenska;

public class WeatherData {

    private String day;
    private String date_time;
    private String location;
    private String temperature;
    private String pressure;
    private String humidity;
    private String sun_set;
    private String sun_rise;
    private String wind_speed;
    private String wind_direction;

    public WeatherData(String day, String date_time, String location, String temperature, String pressure, String humidity, String sun_set, String sun_rise, String wind_speed, String wind_direction) {
        this.day = day;
        this.date_time = date_time;
        this.location = location;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sun_set = sun_set;
        this.sun_rise = sun_rise;
        this.wind_speed = wind_speed;
        this.wind_direction = wind_direction;
    }

    public String getDay() { return day; }

    public String getDate_time() {
        return date_time;
    }

    public String getLocation() {
        return location;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getSun_set() {
        return sun_set;
    }

    public String getSun_rise() {
        return sun_rise;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public String getWind_direction() {
        return wind_direction;
    }
}
