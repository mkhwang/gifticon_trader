package com.example.gifticon_trader.user.infra;

import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserId> {
}
