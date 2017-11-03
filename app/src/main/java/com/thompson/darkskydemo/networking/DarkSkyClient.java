package com.thompson.darkskydemo.networking;

import com.thompson.darkskydemo.DarkSkyApp;
import com.thompson.darkskydemo.json_models.DarkSkyForecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mark Thompson Jr.
 */

public final class DarkSkyClient {

    private static final String BASE_URL = "https://api.darksky.net/";
    private static final String API_KEY = "a6037002fae066947df9bc5932883931/";
    private static final String FORECAST_ENDPOINT =
            "forecast/" + API_KEY;

    private static Retrofit createRetrofitInstance(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public interface DarkSkyAPIService {
        @GET(FORECAST_ENDPOINT + "{latlng}")
        Call<DarkSkyForecast> getForecast(@Path("latlng") String latLng);
    }

    public static DarkSkyAPIService getDarkSkyAPIService(String url) {
        return createRetrofitInstance(url).create(DarkSkyAPIService.class);
    }

    public static void getForecast(double lat, double lng, final DarkSkyCallback callback) {

        if(!DarkSkyApp.isOnline()) {
            callback.onIOFailure(new ConnectivityException("No network available"));
            return;
        }

        Call<DarkSkyForecast> call = getDarkSkyAPIService(BASE_URL).getForecast(latLngToString(lat, lng));

        call.enqueue(new Callback<DarkSkyForecast>() {
            @Override
            public void onResponse(Call<DarkSkyForecast> call, Response<DarkSkyForecast> response) {
                if(response.isSuccessful()) {
                    DarkSkyForecast darkSkyForecast = response.body();
                    callback.onSuccess(darkSkyForecast, response.code());
                } else {
                    callback.onHttpError(response.code(), "");
                }
            }

            @Override
            public void onFailure(Call<DarkSkyForecast> call, Throwable t) {
                callback.onIOFailure(t);
            }
        });
    }

    public interface BaseHTTPCallback {
        /**
         * A error callback for HTTP codes 400...500
         * @param HTTPCode
         * @param reason
         */
        void onHttpError(int HTTPCode, String reason);

        /**
         * There was probably an IO failure
         * @param t
         */
        void onIOFailure(Throwable t);
    }

    public interface DarkSkyCallback extends BaseHTTPCallback {
        /**
         * The web request succeeded with HTTP code 200...300
         * @param darkSkyForecast
         * @param HTTPCode
         */
        void onSuccess(DarkSkyForecast darkSkyForecast, int HTTPCode);
    }

    private static String latLngToString(double lat, double lng) {
        return lat + "," + lng;
    }
}
