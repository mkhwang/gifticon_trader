package com.example.gifticon_trader.unit.user.in;

import com.example.gifticon_trader.config.GenericMapper;
import com.example.gifticon_trader.user.application.UserSearchService;
import com.example.gifticon_trader.user.domain.LoginUser;
import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.in.rest.UserController;
import com.example.gifticon_trader.user.in.rest.dto.MeDto;
import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

  @InjectMocks
  private UserController userController;

  @Mock
  GenericMapper genericMapper;

  @Mock
  UserSearchService userSearchService;

  @BeforeEach
  void setUp() {
    genericMapper = mock(GenericMapper.class);
    userSearchService = mock(UserSearchService.class);
    userController = new UserController(genericMapper, userSearchService);
  }

  @Test
  void getMe_shouldReturnMappedMeDto() {
    // given
    String username = "username";
    String roleName = "ROLE_USER";
    User user = User.register(
            username,
            "tester",
            "1234",
            new BCryptPasswordEncoder()
    );
    LoginUser loginUser = user.toLoginUser(Set.of(new SimpleGrantedAuthority(roleName)));
    MeDto expectedDto = new MeDto();
    expectedDto.setUsername("test@example.com");

    given(genericMapper.toDto(loginUser, MeDto.class)).willReturn(expectedDto);

    // when
    MeDto result = userController.getMe(loginUser);

    // then
    verify(genericMapper).toDto(loginUser, MeDto.class);
    assertThat(result.getUsername()).isEqualTo("test@example.com");
  }

  @Test
  void searchMe_shouldReturnMappedUserDto() {
    // given
    UserSearchDto dto = new UserSearchDto();
    Page<UserDto> mockPage = new PageImpl<>(List.of(new UserDto(1L, "nickname")));
    given(userSearchService.searchUsers(dto)).willReturn(mockPage);

    // when
    Page<UserDto> result = userController.searchUsers(dto);

    // then
    assertThat(result.getContent()).hasSize(1);
    verify(userSearchService).searchUsers(dto);
  }

  @Test
  void getUserProfile_shouldReturnUserDto() {
    // given
    Long userId = 1L;
    UserDto mockUser = new UserDto(userId, "nickname");
    given(userSearchService.getUserProfile(userId)).willReturn(mockUser);

    // when
    UserDto result = userController.getUserProfile(userId);

    // then
    assertThat(result.getNickname()).isEqualTo("nickname");
    verify(userSearchService).getUserProfile(userId);
  }
}