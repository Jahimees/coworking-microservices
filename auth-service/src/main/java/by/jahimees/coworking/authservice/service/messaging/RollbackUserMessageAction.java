package by.jahimees.coworking.authservice.service.messaging;

import by.jahimees.coworking.authservice.data.message.ServiceMessage;
import by.jahimees.coworking.authservice.service.OldObjectCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollbackUserMessageAction extends MessageAction {

    private final OldObjectCacheService oldObjectCacheService;

    @Override
    public void execute(ServiceMessage serviceMessage) {
        //TODO
    }
}
