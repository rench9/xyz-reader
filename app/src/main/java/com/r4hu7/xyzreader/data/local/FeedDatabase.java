package com.r4hu7.xyzreader.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.r4hu7.xyzreader.data.local.dao.FeedDao;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;

@Database(entities = {Feed.class}, version = 1, exportSchema = false)
public abstract class FeedDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "_main";
    private static FeedDatabase INSTANCE;
    public abstract FeedDao feedDao();

    public static FeedDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FeedDatabase.class, DATABASE_NAME).build();
        return INSTANCE;
    }
}
