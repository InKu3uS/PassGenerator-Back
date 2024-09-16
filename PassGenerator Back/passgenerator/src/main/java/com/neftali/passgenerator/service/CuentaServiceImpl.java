package com.neftali.passgenerator.service;

import com.neftali.passgenerator.dto.CuentaDTO;
import com.neftali.passgenerator.dto.CuentaMapper;
import com.neftali.passgenerator.entity.Cuenta;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.CuentaNotFoundException;
import com.neftali.passgenerator.exceptions.DuplicateAccountException;
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

    @Autowired
    private CuentaMapper cuentaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> findAll() throws CuentaNotFoundException {
        List<Cuenta> cuentas = repository.findAll();
        if(cuentas.isEmpty()){
            throw new CuentaNotFoundException("No se han encontrado cuentas guardadas");
        }
        return cuentas.stream().map(cuenta -> cuentaMapper.cuentaToCuentaDTO(cuenta)).toList();
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
    public List<CuentaDTO> findByUser(User user) throws CuentaNotFoundException {
        List<Cuenta> cuentas = repository.findByUser(user);
        if(cuentas.isEmpty()){
            throw new CuentaNotFoundException("No se han encontrado cuentas de ese usuario");
        }
        return cuentas.stream().map(cuenta -> cuentaMapper.cuentaToCuentaDTO(cuenta)).toList();
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
    public Cuenta findByUserAndSite(String mail, String site) throws CuentaNotFoundException, UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(mail);
        if (user.isEmpty()){
            throw new UserNotFoundException("Usuario no encontrado");
        } else {
            try {
                Optional<Cuenta> cuenta = repository.findByUserUuidAndSite(user.get().getUuid(), site);
                return cuenta.orElseThrow(()->new CuentaNotFoundException("Account not found"));
            } catch (Exception e) {
                throw new CuentaNotFoundException("Account not found");
            }
        }
    }

    @Override
    @Transactional
    public void save(Cuenta cuenta) throws UserNotFoundException, DuplicateAccountException {
        //Encuentra al usuario por UUID
        User user = userRepository.findByUuid(cuenta.getUser().getUuid())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Asigna el User recuperado a la Cuenta
        cuenta.setUser(user);

        // Verifica si existe una cuenta con el mismo site para ese usuario
        Optional<Cuenta> cuentaExists = repository.findByUserUuidAndSite(user.getUuid(), cuenta.getSite());

        if(cuentaExists.isPresent()){
            throw new DuplicateAccountException("El usuario ya tiene una cuenta del sitio " + cuenta.getSite());
        }

        cuenta.setCreateTime(getFecha());
        repository.save(cuenta);
    }

    @Override
    public void update(Cuenta cuenta) throws CuentaNotFoundException, UserNotFoundException {
        User user = userRepository.findByUuid(cuenta.getUser().getUuid())
                .orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));

        cuenta.setUser(user);

        Cuenta cuentaExists = repository.findByUserUuidAndSite(user.getUuid(), cuenta.getSite())
                .orElseThrow(()->new CuentaNotFoundException("Cuenta no encontrada"));

        cuentaExists.setPassword(cuenta.getPassword());
        cuentaExists.setExpirationTime(cuenta.getExpirationTime());
        cuentaExists.setCreateTime(getFecha());
        cuentaExists.setNotifiedForExpiration(false);
        cuentaExists.setNotifiedForExpired(false);

        repository.save(cuentaExists);
    }

    @Override
    @Transactional
    public void delete(String site) throws CuentaNotFoundException {
        repository.deleteBySite(site);
    }

    @Override
    public long countByUserMail(String mail) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(mail);
        if(user.isPresent()){
            return repository.countByUser(user.get());
        }
        return 0;
    }

    public String getFecha(){
        LocalDate creationDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return creationDate.format(formatter);
    }
}
