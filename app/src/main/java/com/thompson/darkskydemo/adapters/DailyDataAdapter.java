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
    public DailyDataAdapter(ArrayList<DarkSkyDailyDataBlock> dataSet) {
        mDateFormat = new SimpleDateFormat("EEE");
        mDailyData = dataSet;
    }

    @Override
    public DailyDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.item_wx_daily, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DarkSkyDailyDataBlock dailyDataBlock = mDailyData.get(position);
        Date date = new Date(dailyDataBlock.getTime());
        holder.wxDayText.setText(mDateFormat.format(date));
        holder.wxSummaryText.setText(dailyDataBlock.getSummary());
        holder.wxIconImage.setImageResource(UIHelper.resourceIdForIconName(dailyDataBlock.getIcon()));
        holder.wxLoTempText.setText(Double.toString(dailyDataBlock.getTemperatureLow()));
        holder.wxHiTempText.setText(Double.toString(dailyDataBlock.getTemperatureHigh()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDailyData.size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView wxDayText;
        public ImageView wxIconImage;
        public TextView wxSummaryText;
        public TextView wxHiTempText;
        public TextView wxLoTempText;
        public View itemLayout;

        public ViewHolder(View v) {
            super(v);
            itemLayout = v;
            wxDayText = v.findViewById(R.id.wx_day_text);
            wxIconImage = v.findViewById(R.id.wx_day_icon);
            wxSummaryText = v.findViewById(R.id.wx_day_summary);
            wxHiTempText = v.findViewById(R.id.wx_day_temp_hi);
            wxLoTempText = v.findViewById(R.id.wx_day_temp_lo);
        }
    }



}
