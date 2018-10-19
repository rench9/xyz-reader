package com.r4hu7.xyzreader.ui.activity;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.databinding.ActivityReaderBinding;
import com.r4hu7.xyzreader.di.Component.DaggerRepositoryComponent;
import com.r4hu7.xyzreader.di.module.ContextModule;
import com.r4hu7.xyzreader.ui.view.ReaderToolBar;
import com.r4hu7.xyzreader.ui.view.ReaderToolCardView;
import com.r4hu7.xyzreader.ui.vm.FeedReaderViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReaderActivity extends AppCompatActivity implements ReaderToolCardView.ReaderToolChangeListner {

    public final static String ARG_FEED_ID = "ARG_FEED_ID";
    public final static int SYSTEM_PERMISSION_REQ_CODE = 9001;

    FeedReaderViewModel viewModel;
    ActivityReaderBinding binding;

    Drawable likeDrawable;
    Drawable shareDrawable;

    @BindView(R.id.tbPrimary)
    ReaderToolBar tbPrimary;

    @BindView(R.id.cvReaderTool)
    ReaderToolCardView cvReaderTool;

    @BindView(R.id.svContainer)
    NestedScrollView svContainer;
    @BindView(R.id.tvBody)
    TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reader);

        ButterKnife.bind(this);
        setSupportActionBar(tbPrimary);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initViewModel();
        initClickHandlers();

    }

    private void initClickHandlers() {
        cvReaderTool.addReaderToolChangeListener(this);
        tvBody.setOnClickListener(view -> {
            if (cvReaderTool.getVisibility() == View.VISIBLE)
                cvReaderTool.setVisibility(View.GONE);
            else
                cvReaderTool.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int color = getApplicationContext().getResources().getColor(R.color.dark_shade_2);

        likeDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.like);

        shareDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_share);
        DrawableCompat.setTint(likeDrawable, color);
        DrawableCompat.setTint(shareDrawable, color);
        menu.clear();
        menu.add(0, R.id.action_like, Menu.NONE, R.string.like).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS).setIcon(likeDrawable);
        menu.add(0, R.id.action_share, Menu.NONE, R.string.share).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS).setIcon(shareDrawable);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_like:
                break;
            case R.id.action_share:
                share();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FeedReaderViewModel.class);
        viewModel.setRepository(DaggerRepositoryComponent.builder().contextModule(new ContextModule(getApplicationContext())).build().getFeedsRepository());
        viewModel.setFeed(getIntent().getExtras().getInt(ARG_FEED_ID));
        binding.setVm(viewModel);
        tbPrimary.setDarkTheme();
        this.OnNightModeChange(viewModel.isDarkMode.get());
    }

    @Override
    public void OnFontSizeChange(float sizeInPixel) {
        viewModel.setTextSize(sizeInPixel);
    }

    @Override
    public void OnNightModeChange(boolean isEnabled) {
        viewModel.setIsDarkMode(isEnabled);
        tbPrimary.setNightMode(isEnabled);
        if (isEnabled)
            tbPrimary.setDarkTheme();
        else
            tbPrimary.setLightTheme();

    }

    @Override
    public void OnSystemPermissionRequired() {
        checkForSystemWritePermission();
    }

    public boolean checkForSystemWritePermission() {
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(getApplicationContext());
        } else {
            permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            cvReaderTool.switchToBrightnessTool();
            return true;
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, SYSTEM_PERMISSION_REQ_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, SYSTEM_PERMISSION_REQ_CODE);
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SYSTEM_PERMISSION_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cvReaderTool.switchToBrightnessTool();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void share() {
        if (viewModel.feed.get() == null) {
            Toast.makeText(getApplicationContext(), R.string.no_feed_available, Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(this)
                .setType(getString(R.string.share_content_type))
                .setText(String.format("Hey! look at this awesome article \"{0}\" by {1}", viewModel.feed.get().title, viewModel.feed.get().author))
                .getIntent(), getString(R.string.action_share)));
    }

    public void like() {

    }

}
