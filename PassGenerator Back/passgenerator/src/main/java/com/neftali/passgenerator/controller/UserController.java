package com.neftali.passgenerator.controller;

import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.service.UserService;
import org.mapstruct.control.MappingControl;
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
    public ResponseEntity<?> verifyPassword(@RequestParam String email, @RequestParam String password) throws UserNotFoundException {
        try{
            boolean isValid = service.verifyPassword(email, password);
            return ResponseEntity.ok().body(isValid);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
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
            response.put("message", "Cuenta no encontrada");
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
            response.put("messasge", "Cuenta no encontrada");
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
