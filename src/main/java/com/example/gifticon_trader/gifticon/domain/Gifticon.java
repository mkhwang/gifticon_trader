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

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "code", column = @Column(name = "gift_code"))
  })
  private GiftCode giftCode;

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
    gifticon.giftCode = GiftCode.of(command.getGiftCode());
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

    gifticon.discountRate = 0f;
    if (gifticon.originalPrice.getAmount() > gifticon.price.getAmount()) {
      gifticon.discountRate = (float)
              (gifticon.originalPrice.getAmount() - gifticon.price.getAmount()) / gifticon.originalPrice.getAmount() * 100;
    }
    gifticon.status = GifticonStatus.WAIT_FOR_INSPECT;
    return gifticon;
  }

  public void startInspect() {
    if (this.status.canStartInspect()) {
      this.status = GifticonStatus.INSPECTING;
      registerEvent(new GifticonStartInspectEvent(this.id));
    }
    throw new IllegalStateException("Cannot start inspect when status is not WAIT_FOR_INSPECT");
  }

  public void rejectInspect(String reason) {
    if (this.status.canRejectInspect()) {
      this.status = GifticonStatus.INSPECT_REJECTED;
      if (this.gifticonInspectHistories == null) {
        this.gifticonInspectHistories = new ArrayList<>();
      }
      this.gifticonInspectHistories.add(GifticonInspectRejectHistory.of(this, reason));
      registerEvent(new GifticonOnInspectRejectedEvent(this.id, reason));
    }
    throw new IllegalStateException("Cannot reject inspect when status is not INSPECTING");
  }

  public void passInspect() {
    if (this.status.canRejectInspect()) {
      this.status = GifticonStatus.READY_FOR_SALE;
      registerEvent(new GifticonOnInspectCompleteEvent(this.id));
    }
    throw new IllegalStateException("Cannot reject inspect when status is not INSPECTING");

  }

  public void changePrice(Long amount) {
    if (amount < 0) {
      throw new InvalidPriceException();
    }
    if (this.status.canChangePrice()) {
      this.price = Money.of(amount);
    }
  }

  public void onTrade() {
    if (this.status != GifticonStatus.READY_FOR_SALE) {
      throw new IllegalStateException("Cannot start trade when status is not READY_FOR_SALE");
    }
    this.status = GifticonStatus.ON_TRADE;
  }

  public void completeTrade() {
    if (this.status != GifticonStatus.ON_TRADE) {
      throw new IllegalStateException("Cannot complete trade when status is not ON_TRADE");
    }
    this.status = GifticonStatus.TRADE_COMPLETED;
  }

  public void cancelTrade() {
    if (this.status != GifticonStatus.ON_TRADE) {
      throw new IllegalStateException("Cannot cancel trade when status is not ON_TRADE");
    }
    this.status = GifticonStatus.READY_FOR_SALE;
  }

  public void expire() {
    if (this.expirationDate.isBefore(LocalDate.now()) && this.status.canExpire()) {
      this.status = GifticonStatus.EXPIRED;
    }
    throw new IllegalStateException("Cannot expire when status is not EXPIRED");
  }

  public void settle() {
    if (this.status.canSettle()) {
      this.settled = true;
    }
    throw new IllegalStateException("Cannot settle when status is not TRADE_COMPLETED");
  }

  public void changeGiftCode(String giftCode) {
    if (this.status.canChangeGiftCode()) {
      this.giftCode = GiftCode.of(giftCode);
    }
    throw new IllegalStateException("Cannot change gif code when status is not WAIT_FOR_INSPECT");
  }

  public void changeExpirationDate(LocalDate expirationDate) {
    if (expirationDate.isAfter(LocalDate.now()) && this.status.canChangePrice()) {
      this.expirationDate = expirationDate;
    }
    throw new InvalidExpirationDateException();
  }
}
