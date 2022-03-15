package br.dev.vasconcelos.mycart.rest.controller;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import br.dev.vasconcelos.mycart.exception.InvalidPasswordException;
import br.dev.vasconcelos.mycart.exception.UniqueConstraintException;
import br.dev.vasconcelos.mycart.rest.dto.CredencialsDTO;
import br.dev.vasconcelos.mycart.rest.dto.TokenDTO;
import br.dev.vasconcelos.mycart.rest.dto.UserInsertDTO;
import br.dev.vasconcelos.mycart.rest.dto.UserReturnDTO;
import br.dev.vasconcelos.mycart.security.jwt.JwtService;
import br.dev.vasconcelos.mycart.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(
        value= "/api/user",
        produces = {"application/json","application/xml"},
        consumes = {"application/json","application/xml"}
)
@RequiredArgsConstructor
@Api("User routes")
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Create a new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public UserReturnDTO save(@RequestBody @Valid UserInsertDTO userDTO) {
        try {
            UserProfile userProfile = UserProfile
                                            .builder()
                                            .name(userDTO.getName())
                                            .password(userDTO.getPassword())
                                            .email(userDTO.getEmail())
                                            .build();

            String encriptedPassword = passwordEncoder.encode(userProfile.getPassword());
            LocalDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();

            userProfile.setPassword(encriptedPassword);
            userProfile.setActive(true);
            userProfile.setCreatedAt(now);
            userProfile.setUpdatedAt(now);
            userService.salvar(userProfile);

            return new UserReturnDTO(
                        userProfile.getId(),
                        userProfile.isActive(),
                        userProfile.getName(),
                        userProfile.getEmail(),
                        userProfile.getCreatedAt(),
                        userProfile.getUpdatedAt()
                    );
        } catch (UniqueConstraintException e) {
            throw new ResponseStatusException(UNAUTHORIZED);
        }
    }

    @PostMapping("/auth")
    @ResponseStatus(OK)
    @ApiOperation("Authenticate user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User authenticated"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
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
}
