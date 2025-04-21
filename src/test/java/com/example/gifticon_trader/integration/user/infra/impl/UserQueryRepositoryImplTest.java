package com.example.gifticon_trader.integration.user.infra.impl;

import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import com.example.gifticon_trader.user.infra.UserRepository;
import com.example.gifticon_trader.user.infra.impl.UserQueryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class UserQueryRepositoryImplTest {
  @Autowired
  private UserQueryRepositoryImpl userQueryRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    userRepository.save(User.register("user1", "nickname1", "password", passwordEncoder));
    userRepository.save(User.register("user2", "nickname2", "password", passwordEncoder));
    userRepository.flush();
  }

  @Test
  void searchUsers_shouldReturnMatchingUsers() {
    // given
    UserSearchDto searchDto = new UserSearchDto();
    searchDto.setNickname("nick");
    searchDto.setPage(1);
    searchDto.setSize(10);

    // when
    Page<UserDto> result = userQueryRepository.searchUsers(searchDto);

    // then
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getContent())
            .extracting(UserDto::getNickname)
            .containsExactlyInAnyOrder("nickname1", "nickname2");
  }
}