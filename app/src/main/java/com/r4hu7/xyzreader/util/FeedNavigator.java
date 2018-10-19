package com.r4hu7.xyzreader.util;

import com.r4hu7.xyzreader.data.remote.response.model.Feed;

public interface FeedNavigator {
    void openDetail(int feedId);

    void openDetailDialog(Feed feed);

    void stopLoading();
}
