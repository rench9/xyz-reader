package com.r4hu7.xyzreader.data;

import android.support.annotation.NonNull;

import com.r4hu7.xyzreader.data.local.LocalDataSource;
import com.r4hu7.xyzreader.data.remote.RemoteDataSource;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;

import java.util.List;

public class FeedsRepository implements FeedsDataSource {
    private static FeedsRepository INSTANCE;
    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;
    private boolean isOffine = false;

    private FeedsRepository(@NonNull LocalDataSource localDataSource, @NonNull RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static FeedsRepository getInstance(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        if (INSTANCE == null)
            INSTANCE = new FeedsRepository(localDataSource, remoteDataSource);
        return INSTANCE;
    }

    public void setOffine(boolean offine) {
        isOffine = offine;
    }

    @Override
    public void getFeeds(LoadItemCallback<List<Feed>> callback) {
        if (isOffine)
            localDataSource.getFeeds(callback);
        else remoteDataSource.getFeeds(callback);
    }

    @Override
    public void getFeed(int feedId, LoadItemCallback<Feed> callback) {
        localDataSource.getFeed(feedId, callback);
    }

    @Override
    public void getFavFeeds(LoadItemCallback<List<Feed>> callback) {
        localDataSource.getFavFeeds(callback);
    }

    @Override
    public void isFeedFav(int feedId, LoadItemCallback<Boolean> callback) {
        localDataSource.isFeedFav(feedId, callback);
    }

    @Override
    public void isFeedFav(Feed feed, LoadItemCallback<Boolean> callback) {
        localDataSource.isFeedFav(feed, callback);
    }

    @Override
    public void saveFeeds(Feed[] feeds) {
        if (!isOffine)
            localDataSource.saveFeeds(feeds);
    }

    @Override
    public void saveFeed(Feed feed) {
        if (!isOffine)
            localDataSource.saveFeed(feed);
    }

    @Override
    public void markFavourite(Feed feed) {
        localDataSource.markFavourite(feed);
    }

    @Override
    public void markFavourite(int feedId) {
        localDataSource.markFavourite(feedId);
    }

    @Override
    public void removeFromFavourite(Feed feed) {
        localDataSource.removeFromFavourite(feed);
    }

    @Override
    public void removeFromFavourite(int feedId) {
        localDataSource.removeFromFavourite(feedId);

    }

    @Override
    public void setReadStatus(int feedId, int readStatus) {
        localDataSource.setReadStatus(feedId, readStatus);
    }
}
