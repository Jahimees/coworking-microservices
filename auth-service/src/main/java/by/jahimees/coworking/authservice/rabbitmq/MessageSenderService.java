package by.jahimees.coworking.authservice.rabbitmq;

import by.jahimees.coworking.authservice.AuthServiceApplication;
import by.jahimees.coworking.authservice.data.message.ServiceMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSenderService {

    private final RabbitTemplate template;
    private final ObjectMapper objectMapper = AuthServiceApplication.createMapper();

    public void sendMessage(ServiceMessage serviceMessage, String queueName) {
        try {
            String json = objectMapper.writeValueAsString(serviceMessage);
            template.convertAndSend(queueName, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
