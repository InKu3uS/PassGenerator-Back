package com.neftali.passgenerator.service;

import com.neftali.passgenerator.entity.Cuenta;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.CuentaNotFoundException;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.repository.CuentaRepository;
import com.neftali.passgenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService{

    @Autowired
    private CuentaRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findAll() throws CuentaNotFoundException {
        List<Cuenta> cuentas = repository.findAll();
        if(cuentas.isEmpty()){
            throw new CuentaNotFoundException("No se han encontrado cuentas guardadas");
        }
        return cuentas;
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(String id) throws CuentaNotFoundException {
        Optional<Cuenta> cuenta = repository.findById(id);
        if(cuenta.isEmpty()){
            throw new CuentaNotFoundException("Cuenta con el ID: "+ id + " no encontrada");
        }
        return cuenta.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findByUser(User user) throws CuentaNotFoundException {
        List<Cuenta> cuentas = repository.findByUser(user);
        if(cuentas.isEmpty()){
            throw new CuentaNotFoundException("No se han encontrado cuentas de ese usuario");
        }
        return cuentas;
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findBySite(String site) throws CuentaNotFoundException {
        Optional<Cuenta> cuenta = repository.findBySite(site);
        if(cuenta.isEmpty()){
            throw new CuentaNotFoundException("Cuenta no encontrada");
        }
        return cuenta.get();
    }

    @Override
    @Transactional
    public void save(Cuenta cuenta) throws CuentaNotFoundException, UserNotFoundException {
        Optional<Cuenta> cuentaExists = repository.findBySite(cuenta.getSite());
        if(cuentaExists.isPresent()){
            cuentaExists.get().setPassword(cuenta.getPassword());
            cuentaExists.get().setCreateTime(cuenta.getCreateTime());
            cuentaExists.get().setExpirationTime(cuenta.getExpirationTime());
            cuentaExists.get().setCreateTime(getFecha());
            repository.save(cuentaExists.get());
        }else{
            cuenta.setCreateTime(getFecha());
            repository.save(cuenta);
        }
    }

    @Override
    @Transactional
    public void delete(String site) throws CuentaNotFoundException {
        repository.deleteBySite(site);
    }

    public String getFecha(){
        LocalDate creationDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return creationDate.format(formatter);
    }
}
