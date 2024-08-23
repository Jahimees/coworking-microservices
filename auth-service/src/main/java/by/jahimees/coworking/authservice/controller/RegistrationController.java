package by.jahimees.coworking.authservice.controller;

import by.jahimees.coworking.authservice.data.dto.UserDto;
import by.jahimees.coworking.authservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.authservice.exception.NotEnoughRegistrationData;
import by.jahimees.coworking.authservice.exception.RoleNotFoundException;
import by.jahimees.coworking.authservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.authservice.service.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RegistrationController {

    private final UserDtoService userDtoService;

    @PostMapping("/reg")
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
        } catch (RoleNotFoundException e) {
            return ResponseEntity.badRequest().body("Internal server error (role not found)");
        }

        return ResponseEntity.ok(createdUser);
    }
}
