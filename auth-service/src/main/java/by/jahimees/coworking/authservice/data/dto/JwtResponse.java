package by.jahimees.coworking.authservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse implements DtoEntity {

    private String token;
}
