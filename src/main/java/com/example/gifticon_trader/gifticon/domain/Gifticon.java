package com.example.gifticon_trader.gifticon.domain;

import com.example.gifticon_trader.common.domain.Money;
import com.example.gifticon_trader.gifticon.application.exception.InvalidExpirationDateException;
import com.example.gifticon_trader.gifticon.application.exception.InvalidPriceException;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import com.example.gifticon_trader.gifticon.domain.event.GifticonOnInspectCompleteEvent;
import com.example.gifticon_trader.gifticon.domain.event.GifticonOnInspectRejectedEvent;
import com.example.gifticon_trader.gifticon.domain.event.GifticonStartInspectEvent;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table
@Getter
@Entity(name = "gifticon")
public class Gifticon extends AbstractAggregateRoot<Gifticon> {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String description;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "amount", column = @Column(name = "original_price"))
  })
  private Money originalPrice;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "amount", column = @Column(name = "price"))
  })
  private Money price;

  @Enumerated(EnumType.STRING)
  private GifticonStatus status;

  private LocalDate expirationDate;

  private float discountRate;

  private boolean settled;

  @OneToMany(mappedBy = "gifticon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<GifticonInspectRejectHistory> gifticonInspectHistories;

  protected Gifticon() {
  }

  public static Gifticon fromCommand(GifticonRegisterCommand command) {
    Gifticon gifticon = new Gifticon();
    gifticon.name = command.getName();
    gifticon.description = command.getDescription();
    gifticon.originalPrice = Money.of(command.getOriginalPrice());
    gifticon.price = Money.of(command.getPrice());
    gifticon.expirationDate = command.getExpirationDate();

    LocalDate expirationDate = command.getExpirationDate();
    if (expirationDate.isBefore(LocalDate.now())) {
      throw new InvalidExpirationDateException();
    }

    if (gifticon.originalPrice.getAmount() < 0 || gifticon.price.getAmount() < 0) {
      throw new InvalidPriceException();
    }

    gifticon.discountRate = command.getDiscountRate();
    gifticon.status = GifticonStatus.WAIT_FOR_INSPECT;
    return gifticon;
  }

  public void startInspect() {
    this.status = GifticonStatus.INSPECTING;
    registerEvent(new GifticonStartInspectEvent(this.id));
  }

  public void rejectInspect(String reason) {
    this.status = GifticonStatus.INSPECT_REJECTED;
    if (this.gifticonInspectHistories == null) {
      this.gifticonInspectHistories = new ArrayList<>();
    }
    this.gifticonInspectHistories.add(GifticonInspectRejectHistory.of(this, reason));
    registerEvent(new GifticonOnInspectRejectedEvent(this.id, reason));
  }

  public void passInspect() {
    this.status = GifticonStatus.READY_FOR_SALE;
    registerEvent(new GifticonOnInspectCompleteEvent(this.id));
  }
}
