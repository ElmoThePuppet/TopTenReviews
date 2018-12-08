package com.example.princeofnigeria.tptenreviews.net.event;

import com.example.princeofnigeria.tptenreviews.net.model.ShowModel;
import com.example.princeofnigeria.tptenreviews.net.model.ShowsDBModel;

import java.util.List;

public class onShowsListEvent {
    private ShowsDBModel showsListResponse;

    public onShowsListEvent() {
    }

    public onShowsListEvent(ShowsDBModel showListResponse) {
        this.showsListResponse = showListResponse;
    }


    public ShowsDBModel getShowsListResponse() {
        return showsListResponse;
    }
}
