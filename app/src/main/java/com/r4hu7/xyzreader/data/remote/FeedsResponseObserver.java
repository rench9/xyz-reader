package com.r4hu7.xyzreader.data.remote;

import com.r4hu7.xyzreader.data.FeedsDataSource;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

public class FeedsResponseObserver<T> implements MaybeObserver<T> {
    private FeedsDataSource.LoadItemCallback callback;

    public FeedsResponseObserver(FeedsDataSource.LoadItemCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        callback.onLoading();
    }

    @Override
    public void onSuccess(T t) {
        callback.onItemLoaded(t);
    }

    @Override
    public void onError(Throwable e) {
        callback.onDataNotAvailable(e);
    }

    @Override
    public void onComplete() {

    }
}
