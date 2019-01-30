package com.max.reactive.user.core;

import java.util.Optional;

public interface UserDao {

    Optional<UserDto> findUser(String userName);

}
