package com.thompson.darkskydemo;

import com.thompson.darkskydemo.json_models.DarkSkyForecast;
import com.thompson.darkskydemo.networking.DarkSkyClient;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mark Thompson Jr.
 */

public class DarkSkySmokeTest {

    private static final double sdLat = 32.715736;
    private static final double sdLng = -117.161087;

    @Test
    public void testDarkSkyValidRequest() {
        DarkSkyClient.DarkSkyAPIService apiService = DarkSkyClient.getDarkSkyAPIService();
        Call<DarkSkyForecast> call = apiService.getForecast(DarkSkyClient.latLngToString(sdLat,sdLng));

        try {
            Response<DarkSkyForecast> response = call.execute();
            DarkSkyForecast forecast = response.body();
            assertTrue(response.code() == 200
                    && forecast.getDarkSkyCurrentWx() != null
                    && forecast.getDarkSkyDailyWx() != null);
        } catch (Exception e) {

        }
    }

    @Test
    public void testDarkSkyBadRequest() {
        DarkSkyClient.DarkSkyAPIService apiService = DarkSkyClient.getDarkSkyAPIService();
        Call<DarkSkyForecast> call = apiService.getForecast(DarkSkyClient.latLngToString(-1000,-1000));
        try {
            Response<DarkSkyForecast> response = call.execute();
            DarkSkyForecast forecast = response.body();
            assertTrue(forecast == null && response.code() == 400);
        } catch (Exception e) {

        }
    }
}
