package com.r4hu7.xyzreader.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.r4hu7.xyzreader.R;
import com.r4hu7.xyzreader.data.remote.response.model.Feed;
import com.r4hu7.xyzreader.databinding.AdapterFeedBinding;
import com.r4hu7.xyzreader.databinding.AdapterFeedGridBinding;
import com.r4hu7.xyzreader.ui.vm.FeedDetailViewModel;
import com.r4hu7.xyzreader.util.FeedNavigator;

import java.lang.ref.WeakReference;

public class FeedCardAdapter extends RecyclerView.Adapter<FeedCardAdapter.ViewHolder> {
    private ObservableList<Feed> feeds;
    private ObservableList.OnListChangedCallback listChangedCallback;
    private LayoutInflater inflater;
    private boolean isGridLayout = false;
    private FeedNavigator navigator;

    private WeakReference<FeedCardAdapter> feedCardAdapterWeakReference;

    public FeedCardAdapter(ObservableList<Feed> feeds, FeedNavigator feedNavigator) {
        this.setFeeds(feeds);
        this.navigator = feedNavigator;
        this.feedCardAdapterWeakReference = new WeakReference<>(this);
    }

    public FeedCardAdapter(boolean isGridLayout, ObservableList<Feed> feeds, FeedNavigator feedNavigator) {
        this.isGridLayout = isGridLayout;
        this.setFeeds(feeds);
        this.navigator = feedNavigator;
        this.feedCardAdapterWeakReference = new WeakReference<>(this);
    }

    private void setFeeds(ObservableList<Feed> feeds) {
        this.feeds = feeds;
        if (listChangedCallback == null)
            listChangedCallback = new ObservableList.OnListChangedCallback() {
                @Override
                public void onChanged(ObservableList sender) {
                    if (feedCardAdapterWeakReference.get() != null)
                        notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                    if (feedCardAdapterWeakReference.get() != null)
                        notifyItemRangeChanged(positionStart, itemCount);

                }

                @Override
                public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                    if (feedCardAdapterWeakReference.get() != null)
                        notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                    if (feedCardAdapterWeakReference.get() != null)
                        notifyItemRangeChanged(fromPosition, itemCount);

                }

                @Override
                public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                    if (feedCardAdapterWeakReference.get() != null)
                        notifyItemRangeRemoved(positionStart, itemCount);

                }
            };
        feeds.addOnListChangedCallback(listChangedCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        ViewHolder viewHolder;
        if (isGridLayout)
            viewHolder = new ViewHolder((AdapterFeedGridBinding) DataBindingUtil.inflate(inflater, R.layout.adapter_feed_grid, viewGroup, false));
        else
            viewHolder = new ViewHolder((AdapterFeedBinding) DataBindingUtil.inflate(inflater, R.layout.adapter_feed, viewGroup, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.viewModel.setFeed(feeds.get(i));
        viewHolder.viewModel.setNavigator(navigator);
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        FeedDetailViewModel viewModel;

        ViewHolder(@NonNull AdapterFeedGridBinding adapterFeedGridBinding) {
            super(adapterFeedGridBinding.getRoot());
            viewModel = new FeedDetailViewModel();
            adapterFeedGridBinding.setVm(viewModel);
        }

        ViewHolder(@NonNull AdapterFeedBinding adapterFeedBinding) {
            super(adapterFeedBinding.getRoot());
            viewModel = new FeedDetailViewModel();
            adapterFeedBinding.setVm(viewModel);
        }
    }
}