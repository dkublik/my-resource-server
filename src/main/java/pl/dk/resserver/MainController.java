package pl.dk.resserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MainController {

    @GetMapping("/main")
    String main() {
        return "Hello World";
    }

    @GetMapping("/whoami")
    // @AuthenticationPrincipal(expression="tokenValue")
    public String whoami(@AuthenticationPrincipal Jwt jwt) {

        System.out.println("-----------");
        System.out.println(jwt.getId());
        System.out.println(jwt.getHeaders());
        System.out.println(jwt.getClaims());
        System.out.println(jwt.getAudience());
        System.out.println(jwt.getIssuer());
        System.out.println(jwt.getExpiresAt());
        System.out.println(jwt.getIssuedAt());
        System.out.println(jwt.getSubject());
        System.out.println(jwt.getTokenValue());
        System.out.println("-----------");
        return jwt.getId();
    }
}
