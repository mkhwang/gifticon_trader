package com.example.gifticon_trader.config.security.audit;

import com.example.gifticon_trader.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class AuditBaseEntity extends AuditCreateOnlyBaseEntity {
  @LastModifiedDate
  private Instant lastModifiedDate;

  @LastModifiedBy
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "last_modified_by_user_id")
  private User lastModifiedBy;
}
