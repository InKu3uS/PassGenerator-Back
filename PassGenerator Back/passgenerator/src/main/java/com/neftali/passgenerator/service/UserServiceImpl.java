package com.neftali.passgenerator.service;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.repository.CuentaRepository;
import com.neftali.passgenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() throws UserNotFoundException {
        List<User> users = repository.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("No se han encontrado usuarios");
        }
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUuid(String uuid) throws UserNotFoundException {
        Optional<User> user = repository.findByUuid(uuid);
        if(user.isEmpty()){
            throw new UserNotFoundException("No se ha encontrado al usuario");
        }
        return user.get();
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException("El usuario con EMAIL: "+email+" no encontrado");
        }
        return user.get();
    }

    @Override
    @Transactional
    public void save(User user) throws UserNotFoundException {
        Optional<User> userExists = repository.findByEmail(user.getEmail());
        if(userExists.isPresent()){
            userExists.get().setUsername(user.getUsername());
            userExists.get().setEmail(user.getEmail());
            userExists.get().setPassword(user.getPassword());
            repository.save(userExists.get());
        }else {
            user.setCreateTime(getFecha());
            repository.save(user);
        }
    }
    @Override
    @Transactional
    public void delete(User user) throws UserNotFoundException {
        if(user.getCuentas() != null){
            cuentaRepository.deleteByUserUuid(user.getUuid());
        }
        repository.deleteByUuid(user.getUuid());
    }

    public String getFecha(){
        LocalDate creationDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return creationDate.format(formatter);
    }

}
