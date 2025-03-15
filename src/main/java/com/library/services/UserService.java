package com.library.services;

import com.library.entities.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
}