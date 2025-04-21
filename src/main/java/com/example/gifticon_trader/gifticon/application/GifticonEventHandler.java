package com.example.gifticon_trader.gifticon.application;

import com.example.gifticon_trader.gifticon.domain.event.GifticonOnInspectCompleteEvent;
import com.example.gifticon_trader.gifticon.domain.event.GifticonStartInspectEvent;
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
  public void handleGifticonOnInspectEvent(GifticonStartInspectEvent event) {
    log.debug("handleGifticonOnInspectEvent");
    // TODO : implement the logic to handle the event
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener
  public void handleGifticonOnInspectCompleteEvent(GifticonOnInspectCompleteEvent event) {
    log.debug("handleGifticonOnInspectCompleteEvent");
    // TODO : implement the logic to handle the event
  }
}
