package com.jwt.service;

import java.util.List;

import com.jwt.entity.User;

public interface UserService {

    User addUser(User userDetail);

    List<User> getUsers();

}
