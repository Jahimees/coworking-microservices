package by.jahimees.coworking.authservice.service.jwt;

import by.jahimees.coworking.authservice.data.Role;
import by.jahimees.coworking.authservice.data.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenService {

    @Value("${token.lifetime}")
    private Duration lifetime;
    @Value("${token.secret}")
    private String secret;

    public String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = user.getRoles().stream().map(Role::getAuthority).toList();

        claims.put("roles", roles);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifetime.toMillis());

        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }

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
