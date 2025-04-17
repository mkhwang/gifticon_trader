package com.example.gifticon_trader.web;

import com.example.gifticon_trader.web.dto.RegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

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
}
