package by.jahimees.coworking.authservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto implements DtoEntity {

    private Integer id;
    private String username;
    private String email;

}
