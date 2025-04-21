package com.example.gifticon_trader.gifticon.domain;

import com.example.gifticon_trader.gifticon.application.exception.InvalidExpirationDateException;
import com.example.gifticon_trader.gifticon.application.exception.InvalidPriceException;
import com.example.gifticon_trader.gifticon.domain.command.GifticonRegisterCommand;
import jakarta.persistence.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDate;

@Table
@Entity(name = "gifticon")
public class Gifticon extends AbstractAggregateRoot<Gifticon> {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String description;

  private Money originalPrice;

  @Embedded
  private Money price;

  @Enumerated(EnumType.STRING)
  private GifticonInspectStatus inspectStatus;

  @Enumerated(EnumType.STRING)
  private GifticonStatus status;

  private LocalDate expirationDate;

  private float discountRate;

  private boolean settled;

  protected Gifticon() {}

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
    gifticon.inspectStatus = GifticonInspectStatus.ON_INSPECT;
    gifticon.status = GifticonStatus.INSPECTING;
    return gifticon;
  }
}
