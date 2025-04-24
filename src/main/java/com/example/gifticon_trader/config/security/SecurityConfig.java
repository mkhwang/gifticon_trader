package com.example.gifticon_trader.config.security;

import com.example.gifticon_trader.config.security.endpoint.IsPublicEndpointRequestMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
  private final IsPublicEndpointRequestMatcher isPublicEndpointRequestMatcher;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final SecurityProperties securityProperties;
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            (authorize) ->
                    authorize.requestMatchers(this.securityProperties.getPublicUrls()).permitAll()
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers(this.isPublicEndpointRequestMatcher).permitAll()
                            .requestMatchers("/error").permitAll()
                            .anyRequest().authenticated());

    http.formLogin(form -> form.loginPage("/login")
            .defaultSuccessUrl("/swagger-ui/index.html").permitAll()
            .failureHandler((request, response, exception) -> {
              if (exception instanceof DisabledException) {
                response.sendRedirect("/verify-email?email=" + request.getParameter("username"));
              } else {
                response.sendRedirect("/login?error");
              }
            }));

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(this.userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    ProviderManager providerManager = new ProviderManager(List.of(authProvider));
    providerManager.setAuthenticationEventPublisher(
            new DefaultAuthenticationEventPublisher(applicationEventPublisher));

    return providerManager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("bcrypt", new BCryptPasswordEncoder());
    encoders.put("noop", PlainTextPasswordEncoder.getInstance());

    return new DelegatingPasswordEncoder("bcrypt", encoders);
  }
}
