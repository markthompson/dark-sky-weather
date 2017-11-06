package com.thompson.darkskydemo.activities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thompson.darkskydemo.R;
import com.thompson.darkskydemo.UIHelper;
import com.thompson.darkskydemo.adapters.DailyDataAdapter;
import com.thompson.darkskydemo.json_models.DarkSkyCurrentWx;
import com.thompson.darkskydemo.json_models.DarkSkyDailyDataBlock;
import com.thompson.darkskydemo.json_models.DarkSkyForecast;
import com.thompson.darkskydemo.networking.DarkSkyClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_STRING_DAILYBLOCK = "KEY_STRING_DAILYBLOCK";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView wxCurrentDayText;
    private ImageView wxCurrentIcon;
    private TextView wxCurrentSummaryText;
    private TextView wxCurrentTempText;

    private ArrayList<DarkSkyDailyDataBlock> mDailyDataList = new ArrayList<>();
    private Location mLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



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

    @Override
    protected void onStart() {
        super.onStart();
        getForecast();
    }

    private void getForecast() {
        DarkSkyClient.getForecast(mLoc.getLatitude(), mLoc.getLongitude(), mForecastCallback );
    }

    private void setCurrentWx(DarkSkyCurrentWx darkSkyCurrentWx) {
        wxCurrentIcon.setImageResource(UIHelper.resourceIdForIconName(darkSkyCurrentWx.getIcon()));
        wxCurrentSummaryText.setText(darkSkyCurrentWx.getSummary());
        wxCurrentTempText.setText(UIHelper.temperatureToString(darkSkyCurrentWx.getTemperature()));
    }

    private DarkSkyClient.DarkSkyCallback mForecastCallback = new DarkSkyClient.DarkSkyCallback() {
        @Override
        public void onSuccess(DarkSkyForecast darkSkyForecast, int HTTPCode) {
            //Log.d("TAG", "success: " + darkSkyForecast.toString());
            MainActivity.this.setCurrentWx(darkSkyForecast.getDarkSkyCurrentWx());
            mDailyDataList = darkSkyForecast.getDarkSkyDailyWx().getDailyDataBlocks();
            // specify an adapter
            mAdapter = new DailyDataAdapter(mDailyDataList, mRowClickListener);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
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

    private DailyDataAdapter.RowClickListener mRowClickListener = new DailyDataAdapter.RowClickListener() {
        @Override
        public void onClick(View view, int position) {
            Toast.makeText(MainActivity.this, "Position=" + position, Toast.LENGTH_SHORT).show();
            startDetailActivity(mDailyDataList.get(position));
        }
    };

    private void startDetailActivity(DarkSkyDailyDataBlock dailyDataBlock) {
        Intent intent = new Intent(MainActivity.this, WeatherDetailActivity.class);
        intent.putExtra(KEY_STRING_DAILYBLOCK, dailyDataBlock.toString());
        startActivity(intent);
    }
}
