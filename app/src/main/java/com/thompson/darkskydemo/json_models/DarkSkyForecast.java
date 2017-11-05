package com.thompson.darkskydemo.json_models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Fields auto-gen'd with http://www.jsonschema2pojo.org/
 *
 * Created by Mark Thompson Jr.
 */
public class DarkSkyForecast {

    @SerializedName("latitude")
    @Expose

    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("currently")
    @Expose
    private DarkSkyCurrentWx darkSkyCurrentWx;

    @SerializedName("minutely")
    @Expose (serialize = false, deserialize = false)
    private Object darkSkyMinutely;

    @SerializedName("hourly")
    @Expose (serialize = false, deserialize = false)
    private Object darkSkyHourly;

    @SerializedName("daily")
    @Expose
    private DarkSkyDailyWx darkSkyDailyWx;

    @SerializedName("flags")
    @Expose (serialize = false, deserialize = false)
    private Object flags;

    @SerializedName("offset")
    @Expose
    private int offset;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public int getOffset() {
        return offset;
    }

    public DarkSkyCurrentWx getDarkSkyCurrentWx() {
        return darkSkyCurrentWx;
    }

    public DarkSkyDailyWx getDarkSkyDailyWx() {
        return darkSkyDailyWx;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}