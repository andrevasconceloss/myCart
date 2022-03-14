package br.dev.vasconcelos.mycart.rest.controller;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import br.dev.vasconcelos.mycart.exception.InvalidPasswordException;
import br.dev.vasconcelos.mycart.rest.dto.CredencialsDTO;
import br.dev.vasconcelos.mycart.rest.dto.TokenDTO;
import br.dev.vasconcelos.mycart.security.jwt.JwtService;
import br.dev.vasconcelos.mycart.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserProfile save(@RequestBody @Valid UserProfile userProfile) {
        String encriptedPassword = passwordEncoder.encode(userProfile.getPassword());
        userProfile.setPassword(encriptedPassword);
        return userService.salvar(userProfile);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredencialsDTO credenciais){
        try {
            UserProfile user = UserProfile
                                .builder()
                                .email(credenciais.getEmail())
                                .password(credenciais.getPassword())
                                .build();
            UserDetails userAuthenticated = userService.autenticar(user);
            String token = jwtService.tokenGenerate(user);
            return new TokenDTO(user.getEmail(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(UNAUTHORIZED);
        }
    }

    @GetMapping("/hello")
    public String test() {
        return "Hello world, my cart apllication!";
    }

}
