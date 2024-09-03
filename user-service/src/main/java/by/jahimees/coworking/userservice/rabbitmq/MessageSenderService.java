package by.jahimees.coworking.userservice.rabbitmq;

import by.jahimees.coworking.userservice.UserServiceApplication;
import by.jahimees.coworking.userservice.data.message.ServiceMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSenderService {

    private final RabbitTemplate template;
    private final ObjectMapper objectMapper = UserServiceApplication.createMapper();

    public void sendMessage(ServiceMessage serviceMessage, String queueName) {
        try {
            String json = objectMapper.writeValueAsString(serviceMessage);
            template.convertAndSend(queueName, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
