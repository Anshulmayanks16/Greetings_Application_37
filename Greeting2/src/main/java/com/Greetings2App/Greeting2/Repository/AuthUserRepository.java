package com.Greetings2App.Greeting2.Repository;

import com.Greetings2App.Greeting2.Models.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {
    Optional<AuthUser> findByEmail(String email);

    Optional<AuthUser> findByEmailIgnoreCase(String email);
}
