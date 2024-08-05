package by.jahimees.coworking.authservice.controller;

import by.jahimees.coworking.authservice.data.dto.UserDto;
import by.jahimees.coworking.authservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.authservice.exception.NotEnoughRegistrationData;
import by.jahimees.coworking.authservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.authservice.service.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserDtoService userDtoService;

    @PostMapping("/v1/reg")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        UserDto createdUser;
        try {
            createdUser = userDtoService.create(userDto);
        } catch (NotEnoughRegistrationData e) {
            return ResponseEntity.badRequest().body("User object is empty. Check username, email and password");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User with this username already exists");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }

        return ResponseEntity.ok(createdUser);
    }
}
