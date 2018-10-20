package com.r4hu7.xyzreader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;
import com.r4hu7.xyzreader.ui.fragment.FeedsListFragment;
import com.r4hu7.xyzreader.ui.fragment.SplashScreenFragment;
import com.r4hu7.xyzreader.ui.fragment.SummaryDialogFragment;
import com.r4hu7.xyzreader.util.ActivityUtils;
import com.r4hu7.xyzreader.util.FeedNavigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity implements FeedNavigator {
    private static final String LANDING_FRAGMENT = "LANDING_FRAGMENT";
    private static final String SUMMARY_DIALOG = "SUMMARY_DIALOG";
    private static final String SPLASH_DIALOG = "SPLASH_DIALOG";
    @BindView(R.id.tbPrimary)
    Toolbar toolbar;
    @BindView(R.id.ivLogoBanner)
    ImageView ivLogoBanner;
    private SplashScreenFragment splashScreenFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        if (!ActivityUtils.isSplashGone)
            showSplashDialog();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        FeedsListFragment feedsListFragment = findOrCreateFeedsListFragment();
        feedsListFragment.setFeedNavigator(this);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, 1, Menu.NONE, "Search").setIcon(R.drawable.ic_search).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void openDetail(int feedId) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getApplicationContext(), ReaderActivity.class);
        bundle.putInt(ReaderActivity.ARG_FEED_ID, feedId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void openDetailDialog(Feed feed) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(SUMMARY_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        SummaryDialogFragment fragment = SummaryDialogFragment.newInstance();
        fragment.setFeed(feed);
        fragment.show(getSupportFragmentManager(), SUMMARY_DIALOG);
    }

    @Override
    public void stopLoading() {
        hideSplashDialog();
    }

    @NonNull
    private FeedsListFragment findOrCreateFeedsListFragment() {
        FeedsListFragment fragment =
                (FeedsListFragment) getSupportFragmentManager().findFragmentByTag(LANDING_FRAGMENT);
        if (fragment == null) {
            fragment = FeedsListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.flContainer, LANDING_FRAGMENT);
        }
        return fragment;
    }

    public void showSplashDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(SPLASH_DIALOG);
        if (prev != null) {
            ft.remove(prev);
            return;
        }
        ft.addToBackStack(null);

        splashScreenFragment = new SplashScreenFragment();
        splashScreenFragment.show(getSupportFragmentManager(), SPLASH_DIALOG);
        splashScreenFragment.setCancelable(false);
        ActivityUtils.isSplashGone = true;

    }

    public void hideSplashDialog() {
        if (splashScreenFragment.isVisible())
            splashScreenFragment.animateLogo(ivLogoBanner.getX(), ivLogoBanner.getY(), ivLogoBanner.getMeasuredHeight());
    }
}
