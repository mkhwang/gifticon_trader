package com.example.gifticon_trader.gifticon.domain;

import com.example.gifticon_trader.config.security.audit.AuditCreateOnlyBaseEntity;
import jakarta.persistence.*;

@Entity
public class GifticonInspectHistory extends AuditCreateOnlyBaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @ManyToOne
  @JoinColumn(
      name = "gifticon_id",
      foreignKey = @ForeignKey(name = "fk_gifticon_inspect_history_gifticon")
  )
  private Gifticon gifticon;

  private GifticonStatus gifticonStatus;

  private String comment;
}
