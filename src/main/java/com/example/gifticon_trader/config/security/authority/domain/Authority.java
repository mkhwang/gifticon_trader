package com.example.gifticon_trader.config.security.authority.domain;

import com.example.gifticon_trader.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity(name = "authorities")
@Getter
@Setter
public class Authority implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String authority;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
