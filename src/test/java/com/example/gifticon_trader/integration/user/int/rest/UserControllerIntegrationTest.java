package com.example.gifticon_trader.integration.user.rest;

import com.example.gifticon_trader.user.domain.User;
import com.example.gifticon_trader.user.in.rest.dto.MeDto;
import com.example.gifticon_trader.user.infra.UserRepository;
import com.example.gifticon_trader.util.security.WithLoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    User user = User.register(
            "test@example.user",
            "tester",
            "1234",
            NoOpPasswordEncoder.getInstance()

    );
    user.emailVerified();
    userRepository.save(user);
  }

  @WithLoginUser(username = "test@example.user", role = "ROLE_USER")
  @Test
  void getMe_shouldReturnUserInfo() throws Exception {
    // given

    // when
    MvcResult result = mockMvc.perform(get("/api/user/me"))
            .andExpect(status().isOk())
            .andReturn();

    String content = result.getResponse().getContentAsString();
    MeDto me = objectMapper.readValue(content, MeDto.class);

    // then
    assertThat(me.getUsername()).isEqualTo("test@example.user");
  }

}