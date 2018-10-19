package com.r4hu7.xyzreader.di.module;

import android.content.Context;

import com.r4hu7.xyzreader.data.FeedsRepository;
import com.r4hu7.xyzreader.data.local.FeedDatabase;
import com.r4hu7.xyzreader.data.local.LocalDataSource;
import com.r4hu7.xyzreader.data.remote.Endpoints;
import com.r4hu7.xyzreader.data.remote.RemoteDataSource;
import com.r4hu7.xyzreader.util.AppExecutors;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RetrofitModule.class})
public class RepositoryModule {
    @Provides
    public FeedsRepository feedsRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        return FeedsRepository.getInstance(localDataSource, remoteDataSource);
    }

    @Provides
    public LocalDataSource localDataSource(AppExecutors appExecutors, FeedDatabase database) {
        return LocalDataSource.getInstance(appExecutors, database);
    }

    @Provides
    public RemoteDataSource remoteDataSource(Endpoints endpoints) {
        return RemoteDataSource.getInstance(endpoints);
    }

    @Provides
    public FeedDatabase feedDatabse(Context context) {
        return FeedDatabase.getInstance(context);
    }

    @Provides
    public AppExecutors appExecutors() {
        return new AppExecutors();
    }
}
