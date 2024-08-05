package by.jahimees.coworking.authservice.service.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class CookieService {

    private final JwtTokenService jwtTokenService;

    public Cookie generateAuthCookie(String token) {
        Claims tokenClaims = jwtTokenService.parseToken(token);

        Cookie tokenCookie = new Cookie("__insecure-auth-token", token);
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        tokenCookie.setDomain("localhost");
        tokenCookie.setSecure(false);
        tokenCookie.
                setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(),
                        tokenClaims.getExpiration().toInstant()));
        tokenCookie.setAttribute("SameSite", "Strict");

        return tokenCookie;
    }
}
