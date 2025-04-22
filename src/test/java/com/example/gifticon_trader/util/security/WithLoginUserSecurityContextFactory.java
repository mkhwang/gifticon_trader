package com.example.gifticon_trader.util.security;

import com.example.gifticon_trader.user.domain.LoginUser;
import com.example.gifticon_trader.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;
import java.util.Set;

public class WithLoginUserSecurityContextFactory implements WithSecurityContextFactory<WithLoginUser> {

  @Override
  public SecurityContext createSecurityContext(WithLoginUser annotation) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();

    User user = User.register(
            annotation.username(),
            "tester",
            "1234",
            NoOpPasswordEncoder.getInstance()
    );
    List<SimpleGrantedAuthority> simpleGrantedAuthorities = List.of(
            new SimpleGrantedAuthority(annotation.role())
    );
    LoginUser loginUser = new LoginUser(user, Set.of(new SimpleGrantedAuthority(annotation.role())));
    Authentication auth = new UsernamePasswordAuthenticationToken(loginUser, "password",
            simpleGrantedAuthorities);

    context.setAuthentication(auth);
    return context;
  }
}