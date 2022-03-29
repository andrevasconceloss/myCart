package br.dev.vasconcelos.mycart.domain.repository;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByEmail(String email);
    Optional<UserProfile> findByName(String name);
}
