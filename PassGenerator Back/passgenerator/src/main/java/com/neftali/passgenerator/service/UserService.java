package com.neftali.passgenerator.service;

import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {

    public List<User> findAll() throws UserNotFoundException;

    public User findByUuid(String uuid) throws UserNotFoundException;

    public User findByEmail(String email) throws UserNotFoundException;

    public void save(User user) throws UserNotFoundException;

    public void delete(User user) throws UserNotFoundException;

}
