package by.jahimees.coworking.authservice.controller;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.dto.JwtRequest;
import by.jahimees.coworking.authservice.dto.JwtResponse;
import by.jahimees.coworking.authservice.jwt.JwtTokenUtils;
import by.jahimees.coworking.authservice.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @PreAuthorize(value = "permitAll()")
    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) {
        Authentication auth = null;

        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        String token = jwtTokenUtils.createToken((User) auth.getPrincipal());
        Claims tokenClaims = jwtTokenUtils.parseToken(token);

        Cookie tokenCookie = new Cookie("__insecure-auth-token", token);
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        tokenCookie.setDomain("localhost");
        tokenCookie.setSecure(false);
//        tokenCookie.
//                setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(),
//                        Instant.now() +  tokenClaims.getExpiration()));

        response.addCookie(tokenCookie);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
