package com.r4hu7.xyzreader.data;

import com.r4hu7.xyzreader.data.remote.response.model.Feed;

import java.util.List;

public interface FeedsDataSource {
    void getFeeds(LoadItemCallback<List<Feed>> callback);

    void getFeed(int feedId, LoadItemCallback<Feed> callback);

    void getFavFeeds(LoadItemCallback<List<Feed>> callback);

    void isFeedFav(int feedId, LoadItemCallback<Boolean> callback);

    void isFeedFav(Feed feed, LoadItemCallback<Boolean> callback);

    void saveFeeds(Feed[] feeds);

    void saveFeed(Feed feed);

    void markFavourite(Feed feed);

    void markFavourite(int feedId);

    void removeFromFavourite(Feed feed);

    void removeFromFavourite(int feedId);

    void setReadStatus(int feedId, int readStatus);

    interface LoadItemCallback<T> {
        void onLoading();

        void onItemLoaded(T t);

        void onDataNotAvailable(Throwable e);
    }
}
