package com.max.reactive.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserDao implements UserDao {

    private final Map<String, UserDto> users = new HashMap<>();

    public InMemoryUserDao() {
        users.put("maksym", new UserDto("Maksym Stepanenko", "maksym", 33,
                                        Arrays.asList("jujutsu", "programming", "reading books")));
    }

    @Override
    public Optional<UserDto> findUser(String userName) {
        return Optional.ofNullable(users.get(userName));
    }
}
