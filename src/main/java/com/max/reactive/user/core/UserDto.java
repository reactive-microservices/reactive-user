package com.max.reactive.user.core;

import lombok.Value;

import java.util.List;

@Value
public final class UserDto {

    private final String name;
    private final String nickName;
    private final int age;
    private final List<String> hobbies;
}
