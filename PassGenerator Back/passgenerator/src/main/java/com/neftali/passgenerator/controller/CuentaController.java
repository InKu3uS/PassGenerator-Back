package com.neftali.passgenerator.controller;

import com.neftali.passgenerator.entity.Cuenta;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.CuentaNotFoundException;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.dto.CuentaDTO;
import com.neftali.passgenerator.service.CuentaService;
import com.neftali.passgenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "cuentas")
@CrossOrigin
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

    @PostMapping(value = {"/save"})
    public ResponseEntity<String> save(@RequestBody Cuenta cuenta) throws CuentaNotFoundException, UserNotFoundException {
        try{
            service.save(cuenta);
            return ResponseEntity.ok("Cuenta creada con éxito");
        } catch (CuentaNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(value = {"/{site}"})
    public ResponseEntity<String> delete(@PathVariable String site) throws CuentaNotFoundException {
        Cuenta cuentaExists = service.findBySite(site);
        if(cuentaExists.getId() != null){
            service.delete(site);
            return ResponseEntity.ok("Cuenta borrada con éxito");
        }else{
            return ResponseEntity.notFound().build();
        }

    }

}
