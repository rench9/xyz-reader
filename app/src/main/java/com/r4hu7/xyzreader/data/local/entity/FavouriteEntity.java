package com.r4hu7.xyzreader.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

@Entity(tableName = "_favourite")
@TypeConverters(FeedTypeConverter.class)
public class FavouriteEntity {
    @PrimaryKey
    public int feedId;
}