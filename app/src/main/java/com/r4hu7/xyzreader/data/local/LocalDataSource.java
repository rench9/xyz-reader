package com.r4hu7.xyzreader.data.local;

import android.support.annotation.NonNull;

import com.r4hu7.xyzreader.data.FeedsDataSource;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;
import com.r4hu7.xyzreader.util.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocalDataSource implements FeedsDataSource {

    private static volatile LocalDataSource INSTANCE;
    private FeedDatabase database;
    private AppExecutors mAppExecutors;

    private LocalDataSource(@NonNull AppExecutors appExecutors, @NonNull FeedDatabase database) {
        this.database = database;
        this.mAppExecutors = appExecutors;
    }

    public static LocalDataSource getInstance(@NonNull AppExecutors appExecutors, FeedDatabase feedDatabase) {
        if (INSTANCE == null)
            INSTANCE = new LocalDataSource(appExecutors, feedDatabase);
        return INSTANCE;
    }

    @Override
    public void getFeeds(LoadItemCallback<List<Feed>> callback) {
        Runnable runnable = () -> callback.onItemLoaded(database.feedDao().getFeeds());
        execute(runnable);
    }

    @Override
    public void getFeed(int feedId, LoadItemCallback<Feed> callback) {
        Runnable runnable = () -> callback.onItemLoaded(database.feedDao().getFeed(feedId));
        execute(runnable);
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
        checkNotNull(feeds);
        Runnable runnable = () -> database.feedDao().insert(feeds);
        execute(runnable);
    }

    @Override
    public void saveFeed(Feed feed) {
        checkNotNull(feed);
        Runnable runnable = () -> database.feedDao().insert(feed);
        execute(runnable);
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

    private void execute(Runnable runnable) {
        mAppExecutors.diskIO().execute(runnable);
    }
}
