package com.example.princeofnigeria.tptenreviews.net.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowsDBModel {
    @SerializedName("results")
    @Expose
    private List<ShowModel> showModels;

    public List<ShowModel> getShowModels() {
        return showModels;
    }

    public void setShowModels(List<ShowModel> showModels) {
        this.showModels = showModels;
    }
}
