package com.neftali.passgenerator.auth;

import com.neftali.passgenerator.dto.EmailDTO;
import com.neftali.passgenerator.entity.Role;
import com.neftali.passgenerator.entity.User;
import com.neftali.passgenerator.exceptions.UserNotFoundException;
import com.neftali.passgenerator.jwt.JwtService;
import com.neftali.passgenerator.repository.UserRepository;
import com.neftali.passgenerator.service.IEmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    IEmailService emailService;

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) throws UserNotFoundException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword()));
        }catch(Exception e){
            throw new UserNotFoundException("Invalid email or password");
        }
        User user = repository.findByEmail(request.getMail()).orElseThrow();
        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) throws MessagingException {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createTime(getFecha())
                .role(Role.ROLE_USER)
                .build();

        repository.save(user);

        EmailDTO mail = new EmailDTO(request.getEmail(), "Bienvenido a PassGenerator", request.getUsername());
        emailService.sendMail(mail);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public String getFecha(){
        LocalDate creationDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return creationDate.format(formatter);
    }
}
