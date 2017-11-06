package com.thompson.darkskydemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thompson.darkskydemo.R;
import com.thompson.darkskydemo.UIHelper;
import com.thompson.darkskydemo.json_models.DarkSkyDailyDataBlock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mark Thompson Jr.
 * Vanilla adapter taken and repurposed from Android dev docs
 * https://developer.android.com/training/material/lists-cards.html
 *
 */
public class DailyDataAdapter extends RecyclerView.Adapter<DailyDataAdapter.ViewHolder> {

    private ArrayList<DarkSkyDailyDataBlock> mDailyData;
    private SimpleDateFormat mDateFormat;
    RowClickListener mListener;

    public DailyDataAdapter(ArrayList<DarkSkyDailyDataBlock> dataSet, RowClickListener listener) {
        mDateFormat = new SimpleDateFormat("EEE MMM-dd");
        mDailyData = dataSet;
        mListener = listener;
    }

    @Override
    public DailyDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_wx_daily, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        DarkSkyDailyDataBlock dailyDataBlock = mDailyData.get(position);

        String dayText;
        if(position == 0 ) {
            dayText = "Today";
        } else if(position == 1) {
            dayText = "Tomorrow";
        } else {
            dayText = mDateFormat.format(new Date(dailyDataBlock.getTime() * 1000));
            //Log.d("TAG", "Time: " + dailyDataBlock.getTime() + "|format:" + dayText);
        }

        holder.wxDayText.setText(dayText);
        holder.wxSummaryText.setText(dailyDataBlock.getSummary());
        holder.wxIconImage.setImageResource(UIHelper.resourceIdForIconName(dailyDataBlock.getIcon()));
        holder.wxHiTempText.setText("HI " + UIHelper.temperatureToString(dailyDataBlock.getTemperatureHigh()));
        holder.wxLoTempText.setText("LO " +UIHelper.temperatureToString(dailyDataBlock.getTemperatureLow()));
    }

    @Override
    public int getItemCount() {
        return mDailyData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView wxDayText;
        public ImageView wxIconImage;
        public TextView wxSummaryText;
        public TextView wxHiTempText;
        public TextView wxLoTempText;
        public View itemLayout;

        RowClickListener mListener;

        public ViewHolder(View v, RowClickListener listener) {
            super(v);
            mListener = listener;
            itemLayout = v;
            wxDayText = v.findViewById(R.id.wx_day_text);
            wxIconImage = v.findViewById(R.id.wx_day_icon);
            wxSummaryText = v.findViewById(R.id.wx_day_summary);
            wxHiTempText = v.findViewById(R.id.wx_day_temp_hi);
            wxLoTempText = v.findViewById(R.id.wx_day_temp_lo);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    /**
     * Inspired by:
     * https://android.jlelse.eu/click-listener-for-recyclerview-adapter-2d17a6f6f6c9
     */
    public interface RowClickListener {
        void onClick(View view, int position);
    }
}
