package com.r4hu7.xyzreader.ui.vm;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableFloat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.data.FeedsDataSource;
import com.r4hu7.xyzreader.data.FeedsRepository;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;
import com.r4hu7.xyzreader.di.module.GlideApp;

public class FeedReaderViewModel extends FeedDetailViewModel {

    private int feedId;
    private FeedsRepository repository;
    private ObservableBoolean isDataLoading = new ObservableBoolean();
    public ObservableBoolean isDarkMode = new ObservableBoolean();
    public ObservableFloat textSize = new ObservableFloat();

    public void setFeed(int feedId) {
        this.feedId = feedId;
        if (repository != null)
            repository.getFeed(feedId, new FeedsDataSource.LoadItemCallback<Feed>() {
                @Override
                public void onLoading() {
                    isDataLoading.set(true);
                }

                @Override
                public void onItemLoaded(Feed feed) {
                    FeedReaderViewModel.this.feed.set(feed);
                    isDataLoading.set(false);
                }

                @Override
                public void onDataNotAvailable(Throwable e) {
                    isDataLoading.set(false);
                }
            });
    }

    public void setRepository(FeedsRepository repository) {
        this.repository = repository;
    }

    public void setTextSize(float textSize) {
        this.textSize.set(textSize);
    }

    public void setIsDarkMode(boolean isDarkMode) {
        this.isDarkMode.set(isDarkMode);
    }

    @BindingAdapter("imageSrc")
    public static void setImage(ImageView view, String url) {
/*        ViewPropertyTransition.Animator animationObject = view1 -> {
            view1.setScaleX(1f);
            view1.setScaleY(1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view1, "scaleY", 1.2f, 1f);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view1, "scaleX", 1.2f, 1f);
            scaleX.setInterpolator(new DecelerateInterpolator());
            scaleY.setInterpolator(new DecelerateInterpolator());
            scaleY.setDuration(500);
            scaleX.setDuration(500);
            scaleX.start();
            scaleY.start();
        };*/
        GlideApp.with(view.getContext())
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
//                .transition(GenericTransitionOptions.with(animationObject))
                .load(url)
                .into(view);
    }

    @BindingAdapter("textSize")
    public static void changeFontSize(TextView view, float size) {
        if (size > 0)
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

}