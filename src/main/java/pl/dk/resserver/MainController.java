package pl.dk.resserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MainController {

    @GetMapping("/showJwt")
    Jwt showJwt(@AuthenticationPrincipal Jwt jwt) {
        return jwt;
    }
}
