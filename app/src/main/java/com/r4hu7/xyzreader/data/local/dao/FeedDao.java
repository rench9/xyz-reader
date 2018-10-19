package com.r4hu7.xyzreader.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.r4hu7.xyzreader.data.remote.response.model.Feed;

import java.util.List;

@Dao
public interface FeedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Feed... feeds);

    @Query("SELECT * FROM _feed")
    List<Feed> getFeeds();

    @Query("SELECT * FROM _feed WHERE id =:id")
    Feed getFeed(int id);
}
