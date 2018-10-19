package com.r4hu7.xyzreader.data.remote;

import android.support.annotation.NonNull;

import com.r4hu7.xyzreader.data.FeedsDataSource;
import com.r4hu7.xyzreader.data.remote.request.DiscoverFeeds;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;

import java.util.List;

public class RemoteDataSource implements FeedsDataSource {


    private static RemoteDataSource INSTANCE;
    private DiscoverFeeds discoverFeeds;

    private RemoteDataSource(@NonNull Endpoints endpoints) {
        this.discoverFeeds = new DiscoverFeeds(endpoints);
    }

    public static RemoteDataSource getInstance(Endpoints endpoints) {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSource(endpoints);
        return INSTANCE;
    }

    @Override
    public void getFeeds(LoadItemCallback<List<Feed>> callback) {
        discoverFeeds.getFeeds().subscribe(new FeedsResponseObserver<>(callback));
    }

    @Override
    public void getFeed(int feedId, LoadItemCallback<Feed> callback) {

    }

    @Override
    public void getFavFeeds(LoadItemCallback<List<Feed>> callback) {

    }

    @Override
    public void isFeedFav(int feedId, LoadItemCallback<Boolean> callback) {

    }

    @Override
    public void isFeedFav(Feed feed, LoadItemCallback<Boolean> callback) {

    }

    @Override
    public void saveFeeds(Feed[] feeds) {

    }

    @Override
    public void saveFeed(Feed feed) {

    }

    @Override
    public void markFavourite(Feed feed) {

    }

    @Override
    public void markFavourite(int feedId) {

    }

    @Override
    public void removeFromFavourite(Feed feed) {

    }

    @Override
    public void removeFromFavourite(int feedId) {

    }

    @Override
    public void setReadStatus(int feedId, int readStatus) {

    }
}
