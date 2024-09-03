package by.jahimees.coworking.userservice.service.messaging;

import by.jahimees.coworking.userservice.data.UserDto;
import by.jahimees.coworking.userservice.data.message.ServiceMessage;
import by.jahimees.coworking.userservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.userservice.exception.NotEnoughCreationDataException;
import by.jahimees.coworking.userservice.exception.UserNotFoundException;
import by.jahimees.coworking.userservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.userservice.rabbitmq.MessageSenderService;
import by.jahimees.coworking.userservice.service.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static by.jahimees.coworking.userservice.constants.MessagingConstants.*;

@Service
@RequiredArgsConstructor
public class CreateUserMessageAction extends MessageAction {

    private final UserDtoService userDtoService;
    private final MessageSenderService messageSenderService;

    @Override
    public void execute(ServiceMessage serviceMessage) {
        UserDto userDto = new UserDto();

        Map<String, Object> params = serviceMessage.getPayload();

        userDto.setId((Integer) params.get("id"));
        userDto.setEmail((String) params.get("email"));
        userDto.setUsername((String) params.get("username"));
        userDto.setFirstName((String) params.get("firstName"));
        userDto.setLastName((String) params.get("lastName"));
        userDto.setMiddleName((String) params.get("middleName"));

        try {
            userDtoService.create(userDto, serviceMessage.getRequestId());
        } catch (UsernameAlreadyExistsException e) {
            ServiceMessage errMessage = createErrorMessage(serviceMessage);
            errMessage.setPayload(Map.of("reason", "Username already exists"));
            messageSenderService.sendMessage(errMessage, AUTH_QUEUE);
            return;

        } catch (EmailAlreadyExistsException e) {
            ServiceMessage errMessage = createErrorMessage(serviceMessage);
            errMessage.setPayload(Map.of("reason", "Email already exists"));
            messageSenderService.sendMessage(errMessage, AUTH_QUEUE);
            return;

        } catch (NotEnoughCreationDataException e) {
            ServiceMessage errMessage = createErrorMessage(serviceMessage);
            errMessage.setPayload(Map.of("reason", "Not enough creation data"));
            messageSenderService.sendMessage(errMessage, AUTH_QUEUE);
            return;

        }

        ServiceMessage responseMessage = createMessageCreated(serviceMessage);
        try {
            userDtoService.commit(serviceMessage.getRequestId());
        } catch (UserNotFoundException e) {
            ServiceMessage errMessage = createErrorMessage(serviceMessage);
            errMessage.setPayload(Map.of("reason", "User not found"));
            messageSenderService.sendMessage(errMessage, AUTH_QUEUE);
            return;
        }

        messageSenderService.sendMessage(responseMessage, AUTH_QUEUE);
    }

    private ServiceMessage createErrorMessage(ServiceMessage receivedMessage) {
        ServiceMessage serviceMessage = new ServiceMessage();

        serviceMessage.setStatus(ERROR_STATUS);
        serviceMessage.setRequestId(receivedMessage.getRequestId());
        serviceMessage.setAction(ROLLBACK_ACTION);
        serviceMessage.setSender(USER_SERVICE);

        return serviceMessage;
    }


    private ServiceMessage createMessageCreated(ServiceMessage receivedMessage) {
        ServiceMessage serviceMessage = new ServiceMessage();
        serviceMessage.setSender(USER_SERVICE);
        serviceMessage.setAction(COMMIT_ACTION);
        serviceMessage.setStatus(CREATED_STATUS);
        serviceMessage.setRequestId(receivedMessage.getRequestId());

        return serviceMessage;
    }

}
