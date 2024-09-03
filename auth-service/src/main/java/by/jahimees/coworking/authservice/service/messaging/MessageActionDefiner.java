package by.jahimees.coworking.authservice.service.messaging;

import by.jahimees.coworking.authservice.data.message.ServiceMessage;
import by.jahimees.coworking.authservice.exception.ActionNotDefinedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static by.jahimees.coworking.authservice.constants.MessagingConstants.*;

@Service
@RequiredArgsConstructor
public class MessageActionDefiner {

    private final CommitUserMessageAction commitUserMessageAction;
    private final RollbackUserMessageAction rollbackUserMessageAction;

    public MessageAction defineAction(ServiceMessage serviceMessage) throws ActionNotDefinedException {

        if (serviceMessage.getSender().equals(USER_SERVICE)) {
            switch (serviceMessage.getAction()) {
                case COMMIT_ACTION -> {
                    return commitUserMessageAction;
                }
                case ROLLBACK_ACTION -> {
                    return rollbackUserMessageAction;
                }
            }
        }

        throw new ActionNotDefinedException("Action %s from service %s not defined".formatted(serviceMessage.getAction(), serviceMessage.getSender()));
    }
}
