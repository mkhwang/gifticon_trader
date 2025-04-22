package com.example.gifticon_trader.user.domain;

import com.example.gifticon_trader.user.application.VerificationTokenGenerator;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Table(name = "verification_token")
@Entity(name = "verification_token")
@Getter
public class VerificationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  @OneToOne
  private User user;

  private LocalDateTime expiryDate;

  protected VerificationToken() {

  }

  public boolean isExpired() {
    return expiryDate.isBefore(LocalDateTime.now());
  }

  private VerificationToken(User user, String token, LocalDateTime expiryDate) {
    this.user = user;
    this.token = token;
    this.expiryDate = expiryDate;
  }

  public static VerificationToken issueFor(User user, VerificationTokenGenerator generator) {
    return new VerificationToken(user, generator.generate(), LocalDateTime.now().plusHours(1));
  }

  public String getUsername() {
    return user.getUsername();
  }
}
