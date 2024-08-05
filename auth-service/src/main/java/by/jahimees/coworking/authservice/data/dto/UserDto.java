package by.jahimees.coworking.authservice.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto implements DtoEntity {

    private Integer id;
    private String username;
    private String email;

    /**
     * !RAW password!
     */
    private String rawPassword;
    private List<RoleDto> roles;

}
