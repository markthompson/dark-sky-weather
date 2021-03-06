package com.thompson.darkskydemo.activities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.thompson.darkskydemo.networking.ConnectivityException;
import com.thompson.darkskydemo.networking.DarkSkyClient;

import java.util.ArrayList;

/**
 * Created by Mark Thompson Jr.
 *
 * RecyclerView impl inspired by impl from Android dev docs
 * https://developer.android.com/training/material/lists-cards.html
 */
public class MainActivity extends AppCompatActivity {

    public static final String KEY_STRING_DAILYBLOCK = "KEY_STRING_DAILYBLOCK";
    public static final String KEY_STATE_LAYOUT_MANAGER = "KEY_STATE_LAYOUT_MANAGER";

    private RecyclerView mRecyclerView;
    private DailyDataAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Parcelable mLayoutManagerState;
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
        mAdapter = new DailyDataAdapter(mDailyDataList, mRowClickListener);
        mRecyclerView.setAdapter(mAdapter);

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

        /*
            Force reload of recyclerview on screen rotations
         */
        getForecast();
    }

    private void getForecast() {
        if(!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
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
            mSwipeRefreshLayout.setRefreshing(false);
            MainActivity.this.setCurrentWx(darkSkyForecast.getDarkSkyCurrentWx());
            mDailyDataList = darkSkyForecast.getDarkSkyDailyWx().getDailyDataBlocks();
            mAdapter.updateDataSet(mDailyDataList);
        }

        @Override
        public void onHttpError(int HTTPCode, String reason) {
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Ugh, request error HTTP code " + HTTPCode);
        }

        @Override
        public void onIOFailure(Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            if(t instanceof ConnectivityException) {
                showToast(t.getMessage());
            } else {
                showToast("Ugh, unknown error " + t.getMessage());
            }
        }
    };

    private DailyDataAdapter.RowClickListener mRowClickListener = new DailyDataAdapter.RowClickListener() {
        @Override
        public void onClick(View view, int position) {
            startDetailActivity(mDailyDataList.get(position));
        }
    };

    private void startDetailActivity(DarkSkyDailyDataBlock dailyDataBlock) {
        Intent intent = new Intent(MainActivity.this, WeatherDetailActivity.class);
        intent.putExtra(KEY_STRING_DAILYBLOCK, dailyDataBlock.toString());
        startActivity(intent);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
