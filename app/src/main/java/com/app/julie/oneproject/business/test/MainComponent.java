package com.app.julie.oneproject.business.test;

import dagger.Component;

@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(ScrollingActivity activity);
}

