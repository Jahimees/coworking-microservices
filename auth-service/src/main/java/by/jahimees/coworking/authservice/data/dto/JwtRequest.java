package by.jahimees.coworking.authservice.data.dto;

import lombok.Data;

@Data
public class JwtRequest implements DtoEntity {
    private String username;
    private String password;
}
