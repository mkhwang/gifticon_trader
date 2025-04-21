package com.example.gifticon_trader.gifticon.domain;

import com.example.gifticon_trader.config.security.audit.AuditCreateOnlyBaseEntity;
import jakarta.persistence.*;

@Table
@Entity
public class GifticonInspectRejectHistory extends AuditCreateOnlyBaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(
      name = "gifticon_id",
      foreignKey = @ForeignKey(name = "fk_gifticon_inspect_history_gifticon")
  )
  private Gifticon gifticon;

  private String reason;

  private GifticonInspectRejectHistory(Gifticon gifticon, String reason) {
    this.gifticon = gifticon;
    this.reason = reason;
  }

  public GifticonInspectRejectHistory() {

  }

  public static GifticonInspectRejectHistory of(Gifticon gifticon, String reason) {
    return new GifticonInspectRejectHistory(gifticon, reason);
  }
}
