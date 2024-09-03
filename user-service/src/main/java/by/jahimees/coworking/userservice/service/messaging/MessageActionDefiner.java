package by.jahimees.coworking.userservice.service.messaging;

import by.jahimees.coworking.userservice.data.message.ServiceMessage;
import by.jahimees.coworking.userservice.exception.ActionNotDefinedException;
import by.jahimees.coworking.userservice.service.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static by.jahimees.coworking.userservice.constants.MessagingConstants.*;


@Service
@RequiredArgsConstructor
public class MessageActionDefiner {

    private final UserDtoService userDtoService;
    private final CreateUserMessageAction createUserMessageAction;

    public MessageAction defineAction(ServiceMessage serviceMessage) throws ActionNotDefinedException {

        if (serviceMessage.getSender().equals(AUTH_SERVICE)) {
            if (serviceMessage.getAction().equals(CREATE_ACTION)) {
                return createUserMessageAction;
            }
        }

        throw new ActionNotDefinedException("Action %s from service %s not defined".formatted(serviceMessage.getAction(), serviceMessage.getSender()));
    }
}
