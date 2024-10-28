package com.neftali.passgenerator.service;
import com.neftali.passgenerator.dto.UserDetailsDTO;
import com.neftali.passgenerator.dto.UserMapper;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.repository.CuentaRepository;
import com.neftali.passgenerator.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDetailsDTO> findAll() throws UserNotFoundException {
        List<User> users = repository.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("No se han encontrado usuarios");
        }
        return users.stream().map(user -> userMapper.userToUserDetailsDTO(user)).toList();
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
    public void editUsername(String email, String username) throws UserNotFoundException {
        Optional<User> userExists = repository.findByEmail(email);
        if(userExists.isPresent()){
            userExists.get().setUsername(username);
            repository.save(userExists.get());
        }else {
            throw new UserNotFoundException("Usuario no encontrado");
        }
    }

    @Override
    @Transactional
    public void editPassword(String email, String password) throws UserNotFoundException {
        Optional<User> userExists = repository.findByEmail(email);
        if(userExists.isPresent()){
            userExists.get().setPassword(passwordEncoder.encode(password));
            repository.save(userExists.get());
        }else {
            throw new UserNotFoundException("Usuario no encontrado");
        }
    }

    @Override
    @Transactional
    public void delete(User user) throws UserNotFoundException, MessagingException {
        if(user.getCuentas() != null){
            cuentaRepository.deleteByUserUuid(user.getUuid());
        }
        repository.deleteByUuid(user.getUuid());
        emailService.sendFarewellEmail(user.getEmail(), user.getUsername());
    }

    @Override
    public boolean verifyPassword(String email, String password) throws UserNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        if(user.isPresent()){
            return passwordEncoder.matches(password, user.get().getPassword());
        }else {
            throw new UserNotFoundException("Usuario no encontrado");
        }
    }
}
