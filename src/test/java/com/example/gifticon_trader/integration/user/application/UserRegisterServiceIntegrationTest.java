package com.example.gifticon_trader.integration.user.application;

import com.example.gifticon_trader.user.application.UserRegisterService;
import com.example.gifticon_trader.user.application.exception.DuplicateUsernameException;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.infra.UserRepository;
import com.example.gifticon_trader.web.dto.RegisterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class UserRegisterServiceIntegrationTest {

  @Autowired
  UserRegisterService userRegisterService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  void register_shouldSaveUser() {
    String username = "teset@example.com";
    String password = "123456";
    // given
    RegisterDto dto = new RegisterDto(username, password);

    // when
    userRegisterService.register(dto);

    // then
    User user = userRepository.findByUsername(username).orElseThrow();
    assertThat(user.getUsername()).isEqualTo(username);
    assertThat(user.matchesPassword(password, passwordEncoder)).isTrue();
  }

  @Test
  void register_shouldThrowException_whenUsernameIsDuplicated() {
    String username = "teset@example.com";
    String password = "123456";

    // given
    RegisterDto dto = new RegisterDto(username, password);
    userRegisterService.register(dto); // 최초 등록

    // when & then
    RegisterDto dupDto = new RegisterDto(username, password);
    assertThatThrownBy(() -> userRegisterService.register(dupDto))
            .isInstanceOf(DuplicateUsernameException.class);
  }
}