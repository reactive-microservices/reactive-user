package com.max.reactive.user;

import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(UserDao.class).to(InMemoryUserDao.class);
    }
}
