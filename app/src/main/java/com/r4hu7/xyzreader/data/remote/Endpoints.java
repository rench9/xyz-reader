package com.r4hu7.xyzreader.data.remote;

import com.r4hu7.xyzreader.data.remote.response.model.Feed;

import java.util.List;

import io.reactivex.Maybe;
import retrofit2.http.GET;

public interface Endpoints {

    @GET("xyz-reader-json")
    Maybe<List<Feed>> getFeeds();
}
