package com.example.gifticon_trader.gifticon.application;

import com.example.gifticon_trader.gifticon.domain.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class GifticonEventHandler {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void handleGifticonStartInspectEvent(GifticonStartInspectEvent event) {
    log.debug("handleGifticonOnInspectEvent");
    // TODO : implement the logic to handle the event
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void handleGifticonInspectRejectedEvent(GifticonInspectRejectedEvent event) {
    log.debug("handleGifticonOnInspectRejectedEvent");
    // TODO : implement the logic to handle the event
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void handleGifticonInspectCompleteEvent(GifticonInspectCompleteEvent event) {
    log.debug("handleGifticonOnInspectCompleteEvent");
    // TODO : implement the logic to handle the event
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void handleGifticonCompleteTradeEvent(GifticonCompleteTradeEvent event) {
    log.debug("handleGifticonOnCompleteTradeEvent");
    // TODO : implement the logic to handle the event
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void handleGifticonCancelTradeEvent(GifticonCompleteTradeEvent event) {
    log.debug("handleGifticonOnCompleteTradeEvent");
    // TODO : implement the logic to handle the event
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void handleGifticonExpireEvent(GifticonExpireEvent event) {
    log.debug("handleGifticonExpireEvent");
    // TODO : implement the logic to handle the event
  }
}
