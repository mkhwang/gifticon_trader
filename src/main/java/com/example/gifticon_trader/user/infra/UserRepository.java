package com.example.gifticon_trader.user.infra;

import com.example.gifticon_trader.user.domain.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(@Email String username);
}
