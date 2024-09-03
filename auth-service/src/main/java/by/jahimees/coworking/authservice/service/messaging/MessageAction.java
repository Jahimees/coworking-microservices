package by.jahimees.coworking.authservice.service.messaging;

import by.jahimees.coworking.authservice.data.message.ServiceMessage;

public abstract class MessageAction {

    public abstract void execute(ServiceMessage serviceMessage);
}
