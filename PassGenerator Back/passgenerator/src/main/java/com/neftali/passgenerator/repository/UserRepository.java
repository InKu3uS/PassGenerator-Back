package com.neftali.passgenerator.repository;

import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUuid(String uuid) throws UserNotFoundException;

    Optional<User> findByEmail(String email) throws UserNotFoundException;

    Optional<User> findByUsername(String username) throws UserNotFoundException;

    void deleteByUuid(String uuid) throws UserNotFoundException;
}
