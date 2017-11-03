package com.thompson.darkskydemo;

/**
 * Created by Mark Thompson Jr.
 */

public class UIHelper {

    public static String temperatureToString(double temperature) {
        return (int)temperature + "\u00B0";
    }

    public static int resourceIdForIconName(String name) {
        switch(name) {
            case "clear-day":
                return R.drawable.ic_sunny;
            case "clear-night":
                return R.drawable.ic_clear_night;
            case "rain":
                return R.drawable.ic_rain;
            case "snow":
                return R.drawable.ic_snow;
            case "sleet":
                return R.drawable.ic_sleet;
            case "wind":
                return R.drawable.ic_wind;
            case "fog":
                return R.drawable.ic_cloudy;
            case "cloudy":
                return R.drawable.ic_cloudy;
            case "partly-cloudy-day":
                return R.drawable.ic_partly_cloudy_day;
            case "partly-cloudy-night":
                return R.drawable.ic_partly_cloudy_night;
            default:
                return R.drawable.ic_sunny;
        }
    }
}
