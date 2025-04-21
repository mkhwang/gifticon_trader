package com.example.gifticon_trader.gifticon.domain;

import com.example.gifticon_trader.config.security.audit.AuditBaseEntity;
import jakarta.persistence.*;

@Table
@Entity(name = "gifticon_image")
public class GifticonImage extends AuditBaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "gifticon_id",
      foreignKey = @ForeignKey(name = "fk_gifticon_image_gifticon")
  )
  private Gifticon gifticon;

  private String imageUrl;

  private Integer order;
}
