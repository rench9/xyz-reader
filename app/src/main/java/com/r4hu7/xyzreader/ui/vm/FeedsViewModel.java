package com.r4hu7.xyzreader.ui.vm;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.r4hu7.xyzreader.data.FeedsDataSource;
import com.r4hu7.xyzreader.data.FeedsRepository;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;

import java.util.List;

public class FeedsViewModel extends ViewModel {
    public ObservableList<Feed> feeds = new ObservableArrayList<>();
    private ObservableBoolean isDataLoading = new ObservableBoolean();

    private FeedsRepository feedsRepository;

    public FeedsViewModel(@NonNull FeedsRepository feedsRepository) {
        this.feedsRepository = feedsRepository;
    }

    public void loadArticles() {
        feedsRepository.getFeeds(new FeedsDataSource.LoadItemCallback<List<Feed>>() {
            @Override
            public void onLoading() {
                isDataLoading.set(true);
            }

            @Override
            public void onItemLoaded(List<Feed> feeds) {
                FeedsViewModel.this.feeds.addAll(feeds);
                isDataLoading.set(false);
                feedsRepository.saveFeeds(feeds.toArray(new Feed[0]));
            }

            @Override
            public void onDataNotAvailable(Throwable e) {
                isDataLoading.set(false);
            }
        });
    }

    public boolean isDataLoading() {
        return isDataLoading.get();
    }
}
