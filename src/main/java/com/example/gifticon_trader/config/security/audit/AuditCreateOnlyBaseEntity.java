package com.example.gifticon_trader.config.security.audit;

import com.example.gifticon_trader.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class AuditCreateOnlyBaseEntity {
  @CreatedDate
  @Column(updatable = false)
  private Instant createdDate;

  @CreatedBy
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "created_by_user_id", updatable = false)
  private User createdBy;
}
