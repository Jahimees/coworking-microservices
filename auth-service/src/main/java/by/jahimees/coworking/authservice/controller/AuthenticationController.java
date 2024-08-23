package by.jahimees.coworking.authservice.controller;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.data.dto.JwtResponse;
import by.jahimees.coworking.authservice.data.dto.UserDto;
import by.jahimees.coworking.authservice.service.jwt.CookieService;
import by.jahimees.coworking.authservice.service.jwt.JwtTokenService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final CookieService cookieService;

    @PreAuthorize(value = "permitAll()")
    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto, HttpServletResponse response) {
        Authentication auth;

        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getRawPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Имя пользователя или пароль неверные");
        }

        String token = jwtTokenService.createToken((User) auth.getPrincipal());
        response.addCookie(cookieService.generateAuthCookie(token));

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
