package com.example.gifticon_trader.web;

import com.example.gifticon_trader.user.application.UserRegisterService;
import com.example.gifticon_trader.user.application.exception.DuplicateUsernameException;
import com.example.gifticon_trader.web.dto.RegisterDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
  private final UserRegisterService userRegisterService;

  @GetMapping("/login")
  public String login(Model model) {
    return "login";
  }

  @GetMapping("/register")
  public String showRegister(Model model) {
    RegisterDto registerDto = new RegisterDto();
    model.addAttribute("registerDto", registerDto);
    return "register";
  }

  @PostMapping("/register")
  public String register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "register";
    }

    try {
      userRegisterService.register(registerDto);
    } catch (DuplicateUsernameException e) {
      bindingResult.rejectValue("username", "signup.form.email.duplicated");
      return "register";
    }

    return "redirect:/verify-email?email=" + registerDto.getUsername();
  }
}
