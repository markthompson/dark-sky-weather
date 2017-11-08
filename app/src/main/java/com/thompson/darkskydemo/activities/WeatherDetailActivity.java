package com.thompson.darkskydemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thompson.darkskydemo.R;
import com.thompson.darkskydemo.UIHelper;
import com.thompson.darkskydemo.json_models.DarkSkyDailyDataBlock;

public class WeatherDetailActivity extends AppCompatActivity {
    public TextView wxDayText;
    public ImageView wxIconImage;
    public TextView wxSummaryText;
    public TextView wxHiTempText;
    public TextView wxLoTempText;
    public TextView wxDewPoint;
    public TextView wxHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        Intent intent = getIntent();
        String json = intent.getStringExtra(MainActivity.KEY_STRING_DAILYBLOCK);
        DarkSkyDailyDataBlock dailyDataBlock = new Gson().fromJson(json, DarkSkyDailyDataBlock.class);

        wxDayText = (TextView)findViewById(R.id.wx_day_text);
        wxIconImage = (ImageView)findViewById(R.id.wx_day_icon);
        wxSummaryText = (TextView)findViewById(R.id.wx_day_summary);
        wxHiTempText = (TextView)findViewById(R.id.wx_day_temp_hi);
        wxLoTempText = (TextView)findViewById(R.id.wx_day_temp_lo);
        wxDewPoint = (TextView)findViewById(R.id.wx_day_dewpoint);
        wxHumidity = (TextView)findViewById(R.id.wx_day_humidity);

        wxDayText.setText(UIHelper.unixTimeToString(dailyDataBlock.getTime()));
        wxSummaryText.setText(dailyDataBlock.getSummary());
        wxIconImage.setImageResource(UIHelper.resourceIdForIconName(dailyDataBlock.getIcon()));
        wxHiTempText.setText("HI " + UIHelper.temperatureToString(dailyDataBlock.getTemperatureHigh()));
        wxLoTempText.setText("LO " +UIHelper.temperatureToString(dailyDataBlock.getTemperatureLow()));
        wxDewPoint.setText("Dewpoint " + UIHelper.temperatureToString(dailyDataBlock.getDewPoint()));
        wxHumidity.setText("Humidity " + UIHelper.percentageToString(dailyDataBlock.getHumidity()));

    }
}
