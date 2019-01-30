package com.max.reactive.user;

import com.google.inject.AbstractModule;
import com.max.reactive.user.adapter.InMemoryUserDao;
import com.max.reactive.user.core.UserDao;

public class UserModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserDao.class).to(InMemoryUserDao.class);
    }
}
