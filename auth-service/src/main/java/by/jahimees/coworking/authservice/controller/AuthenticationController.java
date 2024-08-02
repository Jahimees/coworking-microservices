package by.jahimees.coworking.authservice.controller;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.dto.JwtRequest;
import by.jahimees.coworking.authservice.dto.JwtResponse;
import by.jahimees.coworking.authservice.service.JwtTokenService;
import by.jahimees.coworking.authservice.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final CookieService cookieService;

    @PreAuthorize(value = "permitAll()")
    @PostMapping("/api/v1/auth")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) {
        Authentication auth;

        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        String token = jwtTokenService.createToken((User) auth.getPrincipal());
        response.addCookie(cookieService.generateAuthCookie(token));

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
