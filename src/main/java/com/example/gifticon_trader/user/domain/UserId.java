package com.example.gifticon_trader.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class UserId implements Serializable {

  @Column(name = "user_id")
  private Long value;

  protected UserId() {}

  public UserId(Long value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserId)) return false;
    UserId userId = (UserId) o;
    return Objects.equals(value, userId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

}
