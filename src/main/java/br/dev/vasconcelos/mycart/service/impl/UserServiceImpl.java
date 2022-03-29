package br.dev.vasconcelos.mycart.service.impl;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import br.dev.vasconcelos.mycart.domain.repository.UserProfileRepository;
import br.dev.vasconcelos.mycart.exception.InvalidPasswordException;
import br.dev.vasconcelos.mycart.exception.UserNotFoundException;
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

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserProfileRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public UserProfile save(UserDTO dto) throws UserNotFoundException {
        entityManager
                .createNativeQuery(" " +
                        " INSERT INTO USER_PROFILE ( " +
                        "   USERNAME, EMAIL, USER_PASSWORD " +
                        " ) VALUES ( " +
                        "   ?, ?, ? " +
                        " )")
                .setParameter(1, dto.getName())
                .setParameter(2, dto.getEmail())
                .setParameter(3, dto.getPassword())
                .executeUpdate();

        UserProfile user = repository
                .findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException());

        return user;
    }

    public UserDetails auth(CredencialsDTO dto){
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

        String[] roles = new String[]{"ADMIN", "USER"};

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile user = repository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        String[] roles = new String[]{"ADMIN", "USER"};

        return User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    public UserProfile findById(Integer id) {
        UserProfile user = repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return user;
    }
}
