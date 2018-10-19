package com.r4hu7.xyzreader.di.Component;

import com.r4hu7.xyzreader.data.FeedsRepository;
import com.r4hu7.xyzreader.di.module.RepositoryModule;

import dagger.Component;

@Component(modules = {RepositoryModule.class})
public interface RepositoryComponent {
    FeedsRepository getFeedsRepository();
}
