package com.neftali.passgenerator.controller;

import com.neftali.passgenerator.entity.Cuenta;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.CuentaNotFoundException;
import com.neftali.passgenerator.exceptions.DuplicateAccountException;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.dto.CuentaDTO;
import com.neftali.passgenerator.service.CuentaService;
import com.neftali.passgenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "cuentas")
public class CuentaController {

    @Autowired
    CuentaService service;

    @Autowired
    UserService userService;

    @GetMapping(value = {"/list"})
    public ResponseEntity<List<CuentaDTO>> findAll() throws CuentaNotFoundException {
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping(value = {"/user/{mailUser}"})
    public ResponseEntity<List<CuentaDTO>> findByIdUser(@PathVariable String mailUser) throws CuentaNotFoundException, UserNotFoundException {
        User user = userService.findByEmail(mailUser);
        return ResponseEntity.ok(service.findByUser(user));
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<Cuenta> findById(@PathVariable String id) throws CuentaNotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(value = {"/site/{site}"})
    public ResponseEntity<Cuenta> findBySiteAndUser(@RequestBody String mail, @PathVariable String site) throws CuentaNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(service.findByUserAndSite(mail, site));
    }

    @GetMapping(value = {"/count/{mail}"})
    public ResponseEntity<Long> countByUserMail(@PathVariable String mail) throws UserNotFoundException {
        return ResponseEntity.ok(service.countByUserMail(mail));
    }

    @PostMapping(value = {"/save"})
    public ResponseEntity<Map<String,String>> save(@RequestBody Cuenta cuenta) {
        Map<String, String> response = new HashMap<>();
        try{
            service.save(cuenta);
            response.put("status", "CREATED");
            response.put("message", "Cuenta creada con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (DuplicateAccountException e){
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.put("message", "Error desconocido al guardar la cuenta");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(value = {"/update"})
    public ResponseEntity<Map<String, String>> update(@RequestBody Cuenta cuenta) {
        Map<String, String> response = new HashMap<>();
        try {
            service.update(cuenta);
            response.put("status", "CREATED");
            response.put("message", "Cuenta creada con éxito");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException | CuentaNotFoundException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping(value = {"/{site}"})
    public ResponseEntity<Map<String, String>> delete(@PathVariable String site) throws CuentaNotFoundException {
        Map<String,String> response = new HashMap<>();
        Cuenta cuentaExists = service.findBySite(site);
        if(cuentaExists.getId() != null){
            try {
                service.delete(site);
                response.put("status", "OK");
                response.put("message", "Cuenta borrada con éxito");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (CuentaNotFoundException e){
                response.put("message", e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            catch (Exception e) {
                response.put("status", "500");
                response.put("message", "No se ha podido borrar la cuenta");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
