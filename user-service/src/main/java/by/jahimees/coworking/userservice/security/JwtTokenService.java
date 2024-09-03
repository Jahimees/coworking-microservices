package by.jahimees.coworking.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenService {

    @Value("${token.lifetime}")
    private Duration lifetime;
    @Value("${token.secret}")
    private String secret;

    public Claims parseToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        return parseToken(token).get("roles", List.class);
    }
}
