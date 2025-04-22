package com.example.gifticon_trader.util.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithLoginUserSecurityContextFactory.class)
public @interface WithLoginUser {
  String username() default "test@example.user";
  String role() default "ROLE_USER";
}