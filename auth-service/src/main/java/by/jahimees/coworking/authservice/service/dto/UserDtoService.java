package by.jahimees.coworking.authservice.service.dto;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.data.dto.UserDto;
import by.jahimees.coworking.authservice.data.message.ServiceMessage;
import by.jahimees.coworking.authservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.authservice.exception.NotEnoughRegistrationData;
import by.jahimees.coworking.authservice.exception.RoleNotFoundException;
import by.jahimees.coworking.authservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.authservice.rabbitmq.MessageSenderService;
import by.jahimees.coworking.authservice.service.OldObjectCacheService;
import by.jahimees.coworking.authservice.service.UserService;
import by.jahimees.coworking.authservice.service.converter.UserDtoConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static by.jahimees.coworking.authservice.constants.MessagingConstants.*;

@Service
@AllArgsConstructor
public class UserDtoService {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final OldObjectCacheService oldObjectCacheService;
    private final MessageSenderService messageSenderService;

    public UserDto create(UserDto userDto) throws UsernameAlreadyExistsException, NotEnoughRegistrationData,
            EmailAlreadyExistsException, RoleNotFoundException {
        String requestId = UUID.randomUUID().toString();

        User user = userDtoConverter.convertToEntity(userDto);
        user.setStatus("PENDING"); //TODO по умолчанию в базе

        user.setRequestId(requestId);
        User createdUser = userService.create(user);

        oldObjectCacheService.put(requestId, "none");

        UserDto createdDto = userDtoConverter.convertToDto(createdUser);

        ServiceMessage serviceMessage = new ServiceMessage();
        serviceMessage.setSender(AUTH_SERVICE);
        serviceMessage.setRequestId(requestId);
        serviceMessage.setAction(CREATE_ACTION);
        serviceMessage.setPayload(userDtoConverter.convertEntityToMap(user));

        messageSenderService.sendMessage(serviceMessage, USER_QUEUE);

        return createdDto;
    }

    public void commit(String requestId) {
        userService.commit(requestId);
        oldObjectCacheService.pop(requestId);
    }

    public UserDto loadUserByUsername(String username) {
        return userDtoConverter.convertToDto((User) userService.loadUserByUsername(username));
    }

}