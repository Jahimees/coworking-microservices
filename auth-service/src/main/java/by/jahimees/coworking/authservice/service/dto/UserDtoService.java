package by.jahimees.coworking.authservice.service.dto;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.data.dto.UserDto;
import by.jahimees.coworking.authservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.authservice.exception.NotEnoughRegistrationData;
import by.jahimees.coworking.authservice.exception.RoleNotFoundException;
import by.jahimees.coworking.authservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.authservice.service.UserService;
import by.jahimees.coworking.authservice.service.converter.UserDtoConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDtoService {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    public UserDto create(UserDto userDto) throws UsernameAlreadyExistsException, NotEnoughRegistrationData,
            EmailAlreadyExistsException, RoleNotFoundException {
        User user = userDtoConverter.convertToEntity(userDto);
        user.setStatus("PENDING"); //TODO по умолчанию в базе

        User createdUser = userService.create(user);

        return userDtoConverter.convertToDto(createdUser);
    }

    public UserDto loadUserByUsername(String username) {
        return userDtoConverter.convertToDto((User) userService.loadUserByUsername(username));
    }
}
