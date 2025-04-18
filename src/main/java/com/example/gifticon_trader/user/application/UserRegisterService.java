package com.example.gifticon_trader.user.application;

import com.example.gifticon_trader.user.application.exception.DuplicateUsernameException;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.infra.UserRepository;
import com.example.gifticon_trader.web.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void register(RegisterDto registerDto) {
    userRepository.findByUsername(registerDto.getUsername())
        .ifPresent(user -> {
          throw new DuplicateUsernameException();
        });

    User user = User.register(registerDto.getUsername(), registerDto.getPassword(), passwordEncoder);
    userRepository.save(user);
  }
}
