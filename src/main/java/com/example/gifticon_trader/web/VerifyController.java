package com.example.gifticon_trader.web;

import com.example.gifticon_trader.user.application.VerificationTokenService;
import com.example.gifticon_trader.user.application.VerifyEmailResult;
import com.example.gifticon_trader.web.dto.VerificationTokenDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class VerifyController {

  private final VerificationTokenService verificationTokenService;

  @GetMapping("/verify-email")
  public String verifyEmail(@RequestParam String email) {
    if (email == null || email.isEmpty()) {
      return "redirect:/login";
    }

    VerifyEmailResult result = verificationTokenService.process(email);

    return switch (result) {
      case ALREADY_VERIFIED -> "redirect:/login";
      case NEED_VERIFICATION -> "verify-email-waiting";
      case EMAIL_SENT -> "verify-email";
      case FAILURE -> "verify-email-fail";
    };
  }

  @GetMapping("/verify-email-complete")
  public String verifyEmailToken(@ModelAttribute("emailVerificationDto") @Valid VerificationTokenDto dto,
                                 BindingResult bindingResult) {
    try {
      verificationTokenService.verifyEmailToken(dto.getToken());
    } catch (Exception e) {
      bindingResult.rejectValue("token", "user.email.verify.token.expired");
      return "verify-email-fail";
    }
    return "redirect:/login";
  }
}
