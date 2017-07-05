package com.app.julie.oneproject.business.test;

import javax.inject.Inject;

/**
 * Created by hubert
 * <p>
 * Created on 2017/6/23.
 */

public class User {

    public String name;

    @Inject
    public User(String name) {
        this.name = name;
    }
}
