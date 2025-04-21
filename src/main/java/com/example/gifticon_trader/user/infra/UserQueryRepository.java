package com.example.gifticon_trader.user.infra;

import com.example.gifticon_trader.user.in.rest.dto.UserDto;
import com.example.gifticon_trader.user.in.rest.dto.UserSearchDto;
import org.springframework.data.domain.Page;

public interface UserQueryRepository {
  Page<UserDto> searchUsers(UserSearchDto userSearchDto);
}
