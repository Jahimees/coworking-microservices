package by.jahimees.coworking.authservice.controller;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.authservice.exception.NotEnoughRegistrationData;
import by.jahimees.coworking.authservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/v1/reg")
    public ResponseEntity<?> register(@RequestBody User user) {
        User createdUser;
        try {
            createdUser = userService.createUser(user);
        } catch (NotEnoughRegistrationData e) {
            return ResponseEntity.badRequest().body("User object is empty. Check username, email and password");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User with this username already exists");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User with this email already exists");
        }

        //TODO DTO!!

    }
}
