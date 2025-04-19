package com.example.gifticon_trader.unit.user.application;

import com.example.gifticon_trader.user.application.UserRegisterService;
import com.example.gifticon_trader.user.application.exception.DuplicateNicknameException;
import com.example.gifticon_trader.user.application.exception.DuplicateUsernameException;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.infra.UserRepository;
import com.example.gifticon_trader.web.dto.RegisterDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {

  @Mock
  UserRepository userRepository;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  UserRegisterService userRegisterService;

  @Test
  void register_shouldThrowException_whenUsernameIsDuplicated() {
    String username = "test@example.com";
    String password = "123456";
    String nickname = "test";

    // given
    RegisterDto dto = new RegisterDto(username, password, nickname);
    User user = User.register(username, nickname, password, passwordEncoder);
    given(userRepository.findByUsername(username))
            .willReturn(Optional.of(user));

    // when & then
    assertThatThrownBy(() -> userRegisterService.register(dto))
            .isInstanceOf(DuplicateUsernameException.class);
  }

  @Test
  void register_shouldSaveUser_whenValid() {
    // given
    RegisterDto dto = new RegisterDto("new@example.com", "Password123", "new");
    given(userRepository.findByUsername(dto.getUsername()))
            .willReturn(Optional.empty());

    given(passwordEncoder.encode(dto.getPassword()))
            .willReturn("Password123");

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    // when
    userRegisterService.register(dto);

    // then
    verify(userRepository).save(userCaptor.capture());

    User savedUser = userCaptor.getValue();
    assertThat(savedUser.getUsername()).isEqualTo(dto.getUsername());
  }

  @Test
  void register_shouldThrowException_whenNicknameIsDuplicated() {
    // given
    String username = "new@example.com";
    String password = "Password123";
    String nickname = "duplicated";

    RegisterDto dto = new RegisterDto(username, password, nickname);
    User existingUser = User.register("other@example.com", nickname, "somepass", passwordEncoder);

    given(userRepository.findByUsername(username)).willReturn(Optional.empty());
    given(userRepository.findByNickname(nickname)).willReturn(Optional.of(existingUser));

    // when & then
    assertThatThrownBy(() -> userRegisterService.register(dto))
            .isInstanceOf(DuplicateNicknameException.class);
  }
}