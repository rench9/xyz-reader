package com.r4hu7.xyzreader.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r4hu7.xyzreader.data.FeedConfig;
import com.r4hu7.xyzreader.data.remote.Endpoints;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {OkHttpClientModule.class})
public class RetrofitModule {

    @Provides
    public Endpoints endpoints(Retrofit retrofit) {
        return retrofit.create(Endpoints.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(FeedConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }
}
