package com.neftali.passgenerator.controller;

import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping(value = {"/list"})
    public ResponseEntity<List<User
            >> findAll() throws UserNotFoundException {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<User> findByUuid(@PathVariable String uuid) throws UserNotFoundException {
        return ResponseEntity.ok(service.findByUuid(uuid));
    }

    @GetMapping(value = {"/mail/{mail}"})
    public ResponseEntity<User> findByEmail(@PathVariable String mail) throws UserNotFoundException {
        return ResponseEntity.ok(service.findByEmail(mail));
    }

    @DeleteMapping(value = {"/{email}"})
    public ResponseEntity<String> delete(@PathVariable String email) throws UserNotFoundException {
        User existingUser = service.findByEmail(email);
        if(existingUser.getUuid() != null){
            service.delete(existingUser);
            return ResponseEntity.ok("Usuario borrado con éxito");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
