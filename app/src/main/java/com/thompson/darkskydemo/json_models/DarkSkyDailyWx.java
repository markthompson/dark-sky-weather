package com.thompson.darkskydemo.json_models;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Fields auto-gen'd with http://www.jsonschema2pojo.org/
 *
 * Created by Mark Thompson Jr.
 */
public class DarkSkyDailyWx {

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("data")
    @Expose
    private ArrayList<DarkSkyDailyDataBlock> data = null;

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<DarkSkyDailyDataBlock> getDailyDataBlocks() {
        return data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}