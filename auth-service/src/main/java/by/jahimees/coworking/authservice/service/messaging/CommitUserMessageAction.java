package by.jahimees.coworking.authservice.service.messaging;

import by.jahimees.coworking.authservice.data.message.ServiceMessage;
import by.jahimees.coworking.authservice.service.dto.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommitUserMessageAction extends MessageAction {


    private final UserDtoService userDtoService;

    @Override
    public void execute(ServiceMessage serviceMessage) {
        userDtoService.commit(serviceMessage.getRequestId());
    }
}
