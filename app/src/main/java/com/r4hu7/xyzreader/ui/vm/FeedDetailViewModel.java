package com.r4hu7.xyzreader.ui.vm;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;
import com.r4hu7.xyzreader.di.module.GlideApp;
import com.r4hu7.xyzreader.util.FeedNavigator;

import java.lang.ref.WeakReference;

public class FeedDetailViewModel extends ViewModel {
    public ObservableField<Feed> feed = new ObservableField<>();

    private WeakReference<FeedNavigator> navigator;

    public void setFeed(Feed feed) {
        this.feed.set(feed);
    }

    public void setNavigator(FeedNavigator feedNavigator) {
        this.navigator = new WeakReference<>(feedNavigator);
    }

    @BindingAdapter("thumbSrc")
    public static void setThumb(ImageView view, String url) {
        GlideApp.with(view.getContext())
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .load(url)
                .into(view);
    }

    public void openDetails() {
        if (navigator != null && navigator.get() != null)
            navigator.get().openDetail(feed.get().id);
    }

}