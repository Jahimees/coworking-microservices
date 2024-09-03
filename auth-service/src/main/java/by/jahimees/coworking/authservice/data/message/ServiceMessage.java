package by.jahimees.coworking.authservice.data.message;

import lombok.Data;

import java.util.Map;

@Data
public class ServiceMessage {

    private String requestId;
    private String sender;
    private String status;
    private String action;
    private Map<String, Object> payload;
}
