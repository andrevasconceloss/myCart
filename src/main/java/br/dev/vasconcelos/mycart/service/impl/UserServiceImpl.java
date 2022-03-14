package br.dev.vasconcelos.mycart.service.impl;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import br.dev.vasconcelos.mycart.domain.repository.UserProfileRepository;
import br.dev.vasconcelos.mycart.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserProfileRepository repository;

    @Transactional
    public UserProfile salvar(UserProfile usuario){
        return repository.save(usuario);
    }

    public UserDetails autenticar(UserProfile usuario){
        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean isValidPassword = encoder.matches(usuario.getPassword(), user.getPassword());

        if (isValidPassword){
            return user;
        }

        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserProfile user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        String[] roles = new String[]{"ADMIN", "USER"};

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

}
