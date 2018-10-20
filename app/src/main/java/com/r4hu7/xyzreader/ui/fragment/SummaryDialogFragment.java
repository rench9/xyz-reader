package com.r4hu7.xyzreader.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeClipBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;
import com.r4hu7.xyzreader.databinding.DialogFragmentSummaryBinding;
import com.r4hu7.xyzreader.ui.vm.FeedDetailViewModel;

public class SummaryDialogFragment extends DialogFragment {

    private DialogFragmentSummaryBinding binding;
    private FeedDetailViewModel viewModel;
    private Feed feed;

    public SummaryDialogFragment() {

    }

    public static SummaryDialogFragment newInstance() {
        return new SummaryDialogFragment();
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
        if (viewModel != null)
            viewModel.setFeed(feed);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_Dialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_summary, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FeedDetailViewModel.class);
        viewModel.setFeed(feed);
        binding.setVm(viewModel);


    }
}
