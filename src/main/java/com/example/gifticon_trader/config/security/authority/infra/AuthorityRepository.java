package com.example.gifticon_trader.config.security.authority.infra;

import com.example.gifticon_trader.config.security.authority.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

  Collection<Authority> findByUserId(Long userId);
}
