package br.dev.vasconcelos.mycart.rest.controller;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import br.dev.vasconcelos.mycart.exception.InvalidPasswordException;
import br.dev.vasconcelos.mycart.exception.UniqueConstraintException;
import br.dev.vasconcelos.mycart.exception.NotFoundException;
import br.dev.vasconcelos.mycart.rest.dto.CredencialsDTO;
import br.dev.vasconcelos.mycart.rest.dto.TokenDTO;
import br.dev.vasconcelos.mycart.rest.dto.UserDTO;
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
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value= "/api/user")
@RequiredArgsConstructor
@Api("User routes")
public class UserController {

    private final UserServiceImpl service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    @ResponseStatus(CREATED)
    @ApiOperation("Create a new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    public UserProfile save(@RequestBody @Valid UserDTO userDTO) {
        try {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return service.save(userDTO);
        } catch (UniqueConstraintException e) {
            throw new ResponseStatusException(UNAUTHORIZED);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    @ResponseStatus(OK)
    @ApiOperation("Find user by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public UserProfile find(@PathVariable("id") Integer id){
        try {
            return service.findById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = {"application/json"})
    @ResponseStatus(OK)
    @ApiOperation("Find user with filter")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public List<UserProfile> find( UserProfile filter ) {
        try {
            return service.find(filter);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth")
    @ResponseStatus(OK)
    @ApiOperation("Authenticate user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User authenticated"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public TokenDTO authenticate(@RequestBody @Valid CredencialsDTO dto){
        try {
            UserDetails userAuthenticated = service.auth(dto);
            String token = jwtService.tokenGenerate(dto);
            return new TokenDTO(dto.getEmail(), token);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND);
        } catch (InvalidPasswordException e) {
            throw new ResponseStatusException(UNAUTHORIZED);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/token")
    @ResponseStatus(OK)
    @ApiOperation("Validate user token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Boolean tokenValidate(@RequestBody @Valid TokenDTO dto){
        try {
            String token = dto.getToken();
            return jwtService.tokenValidate(token);
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR);
        }
    }
}
