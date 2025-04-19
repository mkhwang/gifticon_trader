package com.example.gifticon_trader.notification.application.resolver.template;

import java.util.Map;

public record EmailTemplate(String templatePath, String subject, Map<String, Object> params) {

}
