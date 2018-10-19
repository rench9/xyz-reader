package com.r4hu7.xyzreader.data.remote.request;

import com.r4hu7.xyzreader.data.remote.Endpoints;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DiscoverFeeds {
    private Endpoints endpoints;

    public DiscoverFeeds(Endpoints endpoints) {
        this.endpoints = endpoints;
    }

    public Maybe<List<Feed>> getFeeds() {
        return endpoints.getFeeds().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
