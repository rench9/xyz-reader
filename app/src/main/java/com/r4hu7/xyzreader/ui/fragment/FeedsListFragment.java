package com.r4hu7.xyzreader.ui.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.databinding.FragmentFeedsListBinding;
import com.r4hu7.xyzreader.di.Component.DaggerRepositoryComponent;
import com.r4hu7.xyzreader.di.module.ContextModule;
import com.r4hu7.xyzreader.ui.adapter.FeedCardAdapter;
import com.r4hu7.xyzreader.ui.view.RecyclerViewItemDecorator;
import com.r4hu7.xyzreader.ui.vm.FeedsViewModel;
import com.r4hu7.xyzreader.util.FeedNavigator;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedsListFragment extends Fragment {

    private static final String FEEDLIST_VM = "FEEDLIST_VM";
    private FeedsViewModel viewModel;
    private RecyclerViewItemDecorator itemDecorator1;
    private RecyclerViewItemDecorator itemDecorator2;
    private RecyclerViewItemDecorator itemDecorator3;

    private FeedCardAdapter adapter1;
    private FeedCardAdapter adapter2;
    private FeedCardAdapter adapter3;

    @BindView(R.id.rvContainer)
    RecyclerView recyclerView;
    @BindView(R.id.rvContainer2)
    RecyclerView recyclerView2;
    @BindView(R.id.rvContainer3)
    RecyclerView recyclerView3;

    private WeakReference<FeedNavigator> feedNavigator;

    private ObservableList.OnListChangedCallback feedOnListChangedCallback;

    public static FeedsListFragment newInstance() {
        return new FeedsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentFeedsListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feeds_list, container, false);
        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    public void setFeedNavigator(FeedNavigator feedNavigator) {
        this.feedNavigator = new WeakReference<>(feedNavigator);
    }

    private void initRecyclerView() {

        adapter1 = new FeedCardAdapter(viewModel.feeds, (FeedNavigator) getActivity());
        adapter2 = new FeedCardAdapter(true, viewModel.feeds, (FeedNavigator) getActivity());
        adapter3 = new FeedCardAdapter(false, viewModel.feeds, (FeedNavigator) getActivity());

        if (itemDecorator1 == null)
            itemDecorator1 = new RecyclerViewItemDecorator(false, OrientationHelper.HORIZONTAL, false, 50);
        if (itemDecorator2 == null)
            itemDecorator2 = new RecyclerViewItemDecorator(true, OrientationHelper.HORIZONTAL, false, 2, 50);
        if (itemDecorator3 == null)
            itemDecorator3 = new RecyclerViewItemDecorator(false, OrientationHelper.HORIZONTAL, false, 50);

        recyclerView.setAdapter(adapter1);
        recyclerView2.setAdapter(adapter2);
        recyclerView3.setAdapter(adapter3);

        recyclerView.addItemDecoration(itemDecorator1);
        recyclerView2.addItemDecoration(itemDecorator2);
        recyclerView3.addItemDecoration(itemDecorator3);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ViewModelProvider.NewInstanceFactory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new FeedsViewModel(DaggerRepositoryComponent.builder().contextModule(new ContextModule(getContext())).build().getFeedsRepository());
            }
        }).get(FEEDLIST_VM, FeedsViewModel.class);
        initObserver();
        initRecyclerView();
        if (viewModel.feeds.isEmpty()) {
            viewModel.loadArticles();
        }
    }

    private void initObserver() {
        if (feedOnListChangedCallback == null)
            feedOnListChangedCallback = new ObservableList.OnListChangedCallback() {
                @Override
                public void onChanged(ObservableList sender) {

                }

                @Override
                public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {

                }

                @Override
                public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                    if (feedNavigator != null && feedNavigator.get() != null)
                        feedNavigator.get().stopLoading();
                }

                @Override
                public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {

                }

                @Override
                public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {

                }
            };
        viewModel.feeds.addOnListChangedCallback(feedOnListChangedCallback);
    }

}
