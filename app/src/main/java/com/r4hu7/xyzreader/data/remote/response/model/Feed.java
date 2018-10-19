package com.r4hu7.xyzreader.data.remote.response.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = Feed.TABLE_NAME)
public class Feed {
    static final String TABLE_NAME = "_feed";
    @PrimaryKey
    public int id;
    public String title;
    public String author;
    public String body;
    public String thumb;
    public String photo;
    public float aspect_ratio;
    public String published_date;

    @Ignore
    public String getDate() {
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
        try {
            date = dateFormat.parse(published_date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Feed.getDate", e.getMessage());
            date = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(date);
    }
}
