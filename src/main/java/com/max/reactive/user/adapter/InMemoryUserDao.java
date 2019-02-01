package com.max.reactive.user.adapter;

import com.google.inject.Singleton;
import com.max.reactive.user.core.UserDao;
import com.max.reactive.user.core.UserDto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class InMemoryUserDao implements UserDao {

    private final Map<String, UserDto> users = new HashMap<>();

    public InMemoryUserDao() {
        users.put("maksym", new UserDto("Maksym Stepanenko", "maksym", 33,
                                        Arrays.asList("jujutsu", "programming", "reading books")));

        users.put("olesia", new UserDto("Olesia Stepanenko", "olesia", 33,
                                        Arrays.asList("cooking", "snowboard")));

        for(int i =0; i < 100; ++i){

            String username = "other-" + i;

            users.put(username, new UserDto(username, username, 100,
                                            Arrays.asList("cooking", "snowboard")));
        }

    }

    @Override
    public Optional<UserDto> findUser(String userName) {
        return Optional.ofNullable(users.get(userName));
    }
}
