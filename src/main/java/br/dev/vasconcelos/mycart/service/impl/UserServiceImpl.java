package br.dev.vasconcelos.mycart.service.impl;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import br.dev.vasconcelos.mycart.domain.repository.UserProfileRepository;
import br.dev.vasconcelos.mycart.exception.InvalidPasswordException;
import br.dev.vasconcelos.mycart.rest.dto.CredencialsDTO;
import br.dev.vasconcelos.mycart.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserProfileRepository repository;

    @Transactional
    public UserProfile save(UserDTO dto) {
        return repository.save(
                UserProfile.builder()
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .password(dto.getPassword())
                        .active(dto.isActive())
                        .build()
        );
    }

    public UserDetails auth(CredencialsDTO dto) throws InvalidPasswordException {
        UserDetails user = loadUserByEmail(dto.getEmail());
        boolean isValidPassword = encoder.matches(dto.getPassword(), user.getPassword());

        if (isValidPassword){
            return user;
        }

        throw new InvalidPasswordException();
    }

    public List<UserProfile> findAll() {
        return repository.findAll();
    }

    public List<UserProfile> find(UserProfile filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);

        return repository.findAll(example);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        UserProfile user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().split(","))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile user = repository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().split(","))
                .build();
    }

    public UserProfile findById(Integer id) throws UsernameNotFoundException {
        UserProfile user = repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return user;
    }
}
