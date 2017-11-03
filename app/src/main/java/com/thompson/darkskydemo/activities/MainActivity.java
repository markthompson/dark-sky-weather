package com.thompson.darkskydemo.activities;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thompson.darkskydemo.R;
import com.thompson.darkskydemo.UIHelper;
import com.thompson.darkskydemo.json_models.DarkSkyCurrentWx;
import com.thompson.darkskydemo.json_models.DarkSkyForecast;
import com.thompson.darkskydemo.networking.DarkSkyClient;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView wxCurrentDayText;
    private ImageView wxCurrentIcon;
    private TextView wxCurrentSummaryText;
    private TextView wxCurrentTempText;

    private DarkSkyClient mDarkSkyClient;
    private DarkSkyForecast mDarkSkyForecast;
    private Location mLoc;

    private DarkSkyClient.DarkSkyCallback mForecastCallback = new DarkSkyClient.DarkSkyCallback() {
        @Override
        public void onSuccess(DarkSkyForecast darkSkyForecast, int HTTPCode) {
            Log.d("TAG", "success: " + darkSkyForecast.toString());
            MainActivity.this.setCurrentWx(darkSkyForecast.getDarkSkyCurrentWx());

        }

        @Override
        public void onHttpError(int HTTPCode, String reason) {
            Log.d("TAG", " code:"+ HTTPCode);
        }

        @Override
        public void onIOFailure(Throwable t) {
            Log.d("TAG", " failure:"+ t.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wxCurrentDayText = (TextView)findViewById(R.id.wx_current_day_text);
        wxCurrentDayText.setText("Now");
        wxCurrentIcon = (ImageView)findViewById(R.id.wx_current_icon);
        wxCurrentSummaryText = (TextView)findViewById(R.id.wx_current_summary);
        wxCurrentTempText = (TextView)findViewById(R.id.wx_current_temp);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getForecast();
            }
        });

         mLoc = new Location(LocationManager.GPS_PROVIDER);
         mLoc.setLatitude(32.715736);
         mLoc.setLongitude(-117.161087);

    }

    private void getForecast() {
        DarkSkyClient.getForecast(mLoc.getLatitude(), mLoc.getLongitude(), mForecastCallback );
    }

    private void setCurrentWx(DarkSkyCurrentWx darkSkyCurrentWx) {
        wxCurrentIcon = (ImageView)findViewById(R.id.wx_current_icon);
        wxCurrentSummaryText.setText(darkSkyCurrentWx.getSummary());
        wxCurrentTempText.setText(UIHelper.temperatureToString(darkSkyCurrentWx.getTemperature()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getForecast();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
