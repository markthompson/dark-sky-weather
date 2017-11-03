package com.thompson.darkskydemo.json_models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Fields auto-gen'd with http://www.jsonschema2pojo.org/
 *
 * Created by Mark Thompson Jr.
 */

public class DarkSkyCurrentWx {

    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("nearestStormDistance")
    @Expose
    private int nearestStormDistance;
    @SerializedName("nearestStormBearing")
    @Expose
    private int nearestStormBearing;
    @SerializedName("precipIntensity")
    @Expose
    private int precipIntensity;
    @SerializedName("precipProbability")
    @Expose
    private int precipProbability;
    @SerializedName("temperature")
    @Expose
    private double temperature;
    @SerializedName("apparentTemperature")
    @Expose
    private double apparentTemperature;
    @SerializedName("dewPoint")
    @Expose
    private double dewPoint;
    @SerializedName("humidity")
    @Expose
    private double humidity;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("windSpeed")
    @Expose
    private double windSpeed;
    @SerializedName("windGust")
    @Expose
    private double windGust;
    @SerializedName("windBearing")
    @Expose
    private int windBearing;
    @SerializedName("cloudCover")
    @Expose
    private double cloudCover;
    @SerializedName("uvIndex")
    @Expose
    private int uvIndex;
    @SerializedName("visibility")
    @Expose
    private int visibility;
    @SerializedName("ozone")
    @Expose
    private double ozone;

    public int getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public int getNearestStormDistance() {
        return nearestStormDistance;
    }

    public int getNearestStormBearing() {
        return nearestStormBearing;
    }

    public int getPrecipIntensity() {
        return precipIntensity;
    }

    public int getPrecipProbability() {
        return precipProbability;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindGust() {
        return windGust;
    }

    public int getWindBearing() {
        return windBearing;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public int getVisibility() {
        return visibility;
    }

    public double getOzone() {
        return ozone;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}