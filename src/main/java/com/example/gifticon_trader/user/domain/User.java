package com.example.gifticon_trader.user.domain;

import com.example.gifticon_trader.config.security.authority.domain.Authority;
import com.example.gifticon_trader.user.domain.event.UserRegisteredEvent;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;


@Table(name = "users")
@Entity(name = "users")
public class User extends AbstractAggregateRoot<User> {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @Column(unique = true)
  private String username;

  @Getter
  private String password;
  private boolean enable;
  @Getter
  private boolean accountNonExpired;
  @Getter
  private boolean emailNonExpired;

  @Getter
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
  private Set<Authority> authorities = new HashSet<>();

  @CreatedDate
  @Column(updatable = false)
  private Instant createdDate;

  @LastModifiedDate
  private Instant lastModifiedDate;

  protected User() {}

  public static User register(String username, String rawPassword, PasswordEncoder encoder) {
    User user = new User();
    user.username = username;
    user.password = encoder.encode(rawPassword);  // 비밀번호 암호화
    user.enable = false;
    user.accountNonExpired = true;
    user.emailNonExpired = true;

    user.registerEvent(new UserRegisteredEvent(user.username));
    return user;
  }

  public boolean isActive() {
    return enable && accountNonExpired && emailNonExpired;
  }

  public void changePassword(String rawPassword, PasswordEncoder encoder) {
    this.password = encoder.encode(rawPassword);
  }

  public boolean matchesPassword(String rawPassword, PasswordEncoder encoder) {
    return encoder.matches(rawPassword, this.password);
  }

  public void emailVerified() {
    this.enable = true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public LoginUser toLoginUser(Set<SimpleGrantedAuthority> authorities) {
    return new LoginUser(this, authorities);
  }
}