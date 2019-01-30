package com.max.reactive.user;

import java.util.Optional;

public interface UserDao {

    Optional<UserDto> findUser(String userName);

}
