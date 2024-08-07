package com.neftali.passgenerator.service;

import com.neftali.passgenerator.entity.Cuenta;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.CuentaNotFoundException;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.dto.CuentaDTO;

import java.util.List;

public interface CuentaService {

    List<CuentaDTO> findAll() throws CuentaNotFoundException;

    Cuenta findById(String id) throws CuentaNotFoundException;

    List<CuentaDTO> findByUser(User user) throws CuentaNotFoundException;

    Cuenta findBySite(String site) throws CuentaNotFoundException;

    void save(Cuenta cuenta) throws CuentaNotFoundException, UserNotFoundException;

    void delete(String site) throws CuentaNotFoundException;
}
