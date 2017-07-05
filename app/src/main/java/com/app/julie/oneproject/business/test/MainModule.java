package com.app.julie.oneproject.business.test;

import dagger.Module;
import dagger.Provides;

@Module
class MainModule {
    private final String name;

    public MainModule(String name) {
        this.name = name;
    }

    @Provides
    String provideName() {
        return name;
    }

}