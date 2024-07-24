package com.neftali.passgenerator.service;

import com.neftali.passgenerator.entity.Cuenta;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.CuentaNotFoundException;
import com.neftali.passgenerator.exceptions.UserNotFoundException;

import java.util.List;

public interface CuentaService {

    public List<Cuenta> findAll() throws CuentaNotFoundException;

    public Cuenta findById(String id) throws CuentaNotFoundException;

    public List<Cuenta> findByUser(User user) throws CuentaNotFoundException;

    public Cuenta findBySite(String site) throws CuentaNotFoundException;

    public void save(Cuenta cuenta) throws CuentaNotFoundException, UserNotFoundException;

    public void delete(String site) throws CuentaNotFoundException;
}
