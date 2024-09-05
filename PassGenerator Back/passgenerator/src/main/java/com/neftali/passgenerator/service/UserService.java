package com.neftali.passgenerator.service;

import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> findAll() throws UserNotFoundException;

    User findByUuid(String uuid) throws UserNotFoundException;

    User findByEmail(String email) throws UserNotFoundException;

    void save(User user) throws UserNotFoundException;

    void delete(User user) throws UserNotFoundException;

}
