package com.neftali.passgenerator.service;

import com.neftali.passgenerator.dto.UserDetailsDTO;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {

    List<UserDetailsDTO> findAll() throws UserNotFoundException;

    User findByUuid(String uuid) throws UserNotFoundException;

    User findByEmail(String email) throws UserNotFoundException;

    void editUsername(String email, String username) throws UserNotFoundException;

    void editPassword(String email, String password) throws UserNotFoundException;

    void delete(User user) throws UserNotFoundException, MessagingException;

    boolean verifyPassword(String email, String password) throws UserNotFoundException;
}
