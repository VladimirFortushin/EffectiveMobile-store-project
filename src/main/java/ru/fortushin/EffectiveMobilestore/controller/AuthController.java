package ru.fortushin.EffectiveMobilestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.fortushin.EffectiveMobilestore.security.RegistrationService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    String getLoginPage() {
        return "auth/login";
    }
}
