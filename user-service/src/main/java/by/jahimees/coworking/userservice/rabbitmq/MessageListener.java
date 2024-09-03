package by.jahimees.coworking.userservice.rabbitmq;

import by.jahimees.coworking.userservice.UserServiceApplication;
import by.jahimees.coworking.userservice.data.message.ServiceMessage;
import by.jahimees.coworking.userservice.exception.ActionNotDefinedException;
import by.jahimees.coworking.userservice.service.messaging.MessageAction;
import by.jahimees.coworking.userservice.service.messaging.MessageActionDefiner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static by.jahimees.coworking.userservice.constants.MessagingConstants.USER_QUEUE;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final MessageActionDefiner messageActionDefiner;
    private final ObjectMapper objectMapper = UserServiceApplication.createMapper();

    @RabbitListener(queues = {USER_QUEUE})
    public void processAuthQueue(String message) {
        try {
            ServiceMessage receivedMessage = objectMapper.readValue(message, ServiceMessage.class);

            MessageAction messageAction = messageActionDefiner.defineAction(receivedMessage);

            messageAction.execute(receivedMessage);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ActionNotDefinedException e) {
            throw new RuntimeException(e);
        }
    }
}
