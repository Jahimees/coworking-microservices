package by.jahimees.coworking.userservice.data;

import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String username;
}
