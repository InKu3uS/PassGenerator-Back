package com.neftali.passgenerator.controller;

import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping(value = {"/verify-password"})
    public ResponseEntity<?> verifyPassword(@RequestParam String email, @RequestParam String password) {
        try{
            boolean isValid = service.verifyPassword(email, password);
            return ResponseEntity.ok().body(isValid);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping(value = {"/username"})
    public ResponseEntity<Map<String, String>> editUsername(@RequestParam String email, @RequestParam String username) {
        Map<String, String> response = new HashMap<>();
        try{
            service.editUsername(email,username);
            response.put("status", "OK");
            response.put("message", "Nombre de usuario modificado con éxito");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UserNotFoundException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping(value = {"/password"})
    public ResponseEntity<Map<String, String>> editPassword(@RequestParam String email, @RequestParam String password) {
        Map<String, String> response = new HashMap<>();
        try {
            service.editPassword(email, password);
            response.put("status", "OK");
            response.put("message", "Contraseña modificada con éxito");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UserNotFoundException e) {
            response.put("messasge", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping(value = {"/list"})
    public ResponseEntity<List<User>> findAll() throws UserNotFoundException {
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
    public ResponseEntity<Map<String, String>> delete(@PathVariable String email) throws UserNotFoundException, MessagingException {
        Map<String, String> response = new HashMap<>();
        User existingUser = service.findByEmail(email);
        if(existingUser.getUuid() != null){
            try {
                service.delete(existingUser);
                response.put("status", "OK");
                response.put("message", "Usuario borrado con éxito");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (RuntimeException e) {
                response.put("status", "500");
                response.put("message", "No se ha podido borrar el usuario");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}

