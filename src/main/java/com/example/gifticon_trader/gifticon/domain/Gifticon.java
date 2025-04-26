package com.example.gifticon_trader.gifticon.domain;

import com.example.gifticon_trader.common.domain.Money;
import com.example.gifticon_trader.config.security.audit.AggregateAuditEntity;
import com.example.gifticon_trader.gifticon.application.exception.*;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import com.example.gifticon_trader.gifticon.domain.event.GifticonInspectCompleteEvent;
import com.example.gifticon_trader.gifticon.domain.event.GifticonInspectRejectedEvent;
import com.example.gifticon_trader.gifticon.domain.event.GifticonStartInspectEvent;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table
@Getter
@Entity(name = "gifticon")
public class Gifticon extends AggregateAuditEntity<Gifticon> {
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

  @ElementCollection
  private List<String> tags;

  @OneToMany(mappedBy = "gifticon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<GifticonInspectRejectHistory> gifticonInspectHistories;

  protected Gifticon() {
  }

  public static Gifticon fromCommand(GifticonRegisterCommand command) {
    Gifticon gifticon = new Gifticon();
    gifticon.name = command.name();
    gifticon.description = command.description();
    gifticon.giftCode = GiftCode.of(command.giftCode());
    gifticon.originalPrice = Money.of(command.originalPrice());
    gifticon.price = Money.of(command.price());
    gifticon.expirationDate = command.expirationDate();
    gifticon.tags = command.tags();

    if (gifticon.expirationDate.isBefore(LocalDate.now())) {
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
    throw new CanNotStartInspectException();
  }

  public void rejectInspect(String reason) {
    if (this.status.canRejectInspect()) {
      this.status = GifticonStatus.INSPECT_REJECTED;
      if (this.gifticonInspectHistories == null) {
        this.gifticonInspectHistories = new ArrayList<>();
      }
      this.gifticonInspectHistories.add(GifticonInspectRejectHistory.of(this, reason));
      registerEvent(new GifticonInspectRejectedEvent(this.id, reason));
    }
    throw new CanNotRejectInspectException();
  }

  public void requestInspect() {
    if (this.status.canRequestInspect()) {
      this.status = GifticonStatus.WAIT_FOR_INSPECT;
    }
    throw new CanNotRequestInspectException();
  }

  public void passInspect() {
    if (this.status.canRejectInspect()) {
      this.status = GifticonStatus.READY_FOR_SALE;
      registerEvent(new GifticonInspectCompleteEvent(this.id));
    }
    throw new CanNotPassInspectStatusException();
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
    if (this.status.canTrade()) {
      this.status = GifticonStatus.ON_TRADE;
    }
    throw new CanNotOnTradeException();
  }

  public void completeTrade() {
    if (this.status.canTradeComplete()) {
      this.status = GifticonStatus.TRADE_COMPLETED;
    }
    throw new CanNotCompleteTradeException();
  }

  public void cancelTrade() {
    if (this.status.canTradeCancel()) {
      this.status = GifticonStatus.READY_FOR_SALE;
    }
    throw new CanNotCancelTradeException();
  }

  public void expire() {
    if (this.expirationDate.isBefore(LocalDate.now()) && this.status.canExpire()) {
      this.status = GifticonStatus.EXPIRED;
    }
    throw new CanNotGifticonExpireException();
  }

  public void settle() {
    if (this.status.canSettle()) {
      this.settled = true;
    }
    throw new CanNotGifticonSettleException();
  }

  public void changeGiftCode(String giftCode) {
    if (this.status.canChangeGiftCode()) {
      this.giftCode = GiftCode.of(giftCode);
    }
    throw new CanNotGifticonChangeCodeException();
  }

  public void changeExpirationDate(LocalDate expirationDate) {
    if (expirationDate.isAfter(LocalDate.now()) && this.status.canChangePrice()) {
      this.expirationDate = expirationDate;
    }
    throw new InvalidExpirationDateException();
  }
}
