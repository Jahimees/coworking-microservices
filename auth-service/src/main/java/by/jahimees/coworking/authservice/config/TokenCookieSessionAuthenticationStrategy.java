package by.jahimees.coworking.authservice.config;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.jwt.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@Component
@RequiredArgsConstructor
public class TokenCookieSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response)
            throws SessionAuthenticationException {

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            String token = jwtTokenUtils.createToken((User) authentication);
            Claims tokenClaims = jwtTokenUtils.parseToken(token);

            Cookie tokenCookie = new Cookie("__insecure-auth-token", token);
            tokenCookie.setPath("/");
            tokenCookie.setHttpOnly(true);
            tokenCookie.setDomain("localhost");
            tokenCookie.setSecure(false);
            tokenCookie.
                    setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(),
                            (Temporal) tokenClaims.getExpiration()));

            response.addCookie(tokenCookie);
        }
    }
}
