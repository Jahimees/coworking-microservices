package by.jahimees.coworking.userservice.service;

import by.jahimees.coworking.userservice.data.User;
import by.jahimees.coworking.userservice.data.UserDto;
import by.jahimees.coworking.userservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.userservice.exception.NotEnoughCreationDataException;
import by.jahimees.coworking.userservice.exception.UserNotFoundException;
import by.jahimees.coworking.userservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.userservice.service.converter.UserDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDtoService {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    public UserDto create(UserDto userDto, String requestId) throws UsernameAlreadyExistsException, EmailAlreadyExistsException, NotEnoughCreationDataException {
        User user = userDtoConverter.convertToEntity(userDto);
        user.setStatus("PENDING"); //TODO по умолчанию в базе
        user.setRequestId(requestId);

        User createdUser = userService.create(user);

        return userDtoConverter.convertToDto(createdUser);
    }

    public void commit(String requestId) throws UserNotFoundException {
        userService.commit(requestId);
    }
}
