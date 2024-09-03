package by.jahimees.coworking.userservice.service.messaging;

import by.jahimees.coworking.userservice.data.message.ServiceMessage;
import by.jahimees.coworking.userservice.rabbitmq.MessageSenderService;

public abstract class MessageAction {

    public abstract void execute(ServiceMessage serviceMessage);
}
