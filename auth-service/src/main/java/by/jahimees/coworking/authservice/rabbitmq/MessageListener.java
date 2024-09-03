package by.jahimees.coworking.authservice.rabbitmq;

import by.jahimees.coworking.authservice.AuthServiceApplication;
import by.jahimees.coworking.authservice.data.message.ServiceMessage;
import by.jahimees.coworking.authservice.exception.ActionNotDefinedException;
import by.jahimees.coworking.authservice.service.messaging.MessageAction;
import by.jahimees.coworking.authservice.service.messaging.MessageActionDefiner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static by.jahimees.coworking.authservice.constants.MessagingConstants.AUTH_QUEUE;

@Component
@RequiredArgsConstructor
public class MessageListener {

    private final MessageActionDefiner actionDefiner;
    private final ObjectMapper objectMapper = AuthServiceApplication.createMapper();

    @RabbitListener(queues = {AUTH_QUEUE})
    public void processAuthQueue(String message) {
        try {
            ServiceMessage receivedMessage = objectMapper.readValue(message, ServiceMessage.class);

            MessageAction messageAction = actionDefiner.defineAction(receivedMessage);

            messageAction.execute(receivedMessage);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ActionNotDefinedException e) {
            throw new RuntimeException(e);
        }
    }
}
