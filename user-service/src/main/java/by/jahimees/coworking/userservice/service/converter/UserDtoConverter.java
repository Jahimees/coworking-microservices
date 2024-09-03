package by.jahimees.coworking.userservice.service.converter;


import by.jahimees.coworking.userservice.data.User;
import by.jahimees.coworking.userservice.data.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserDtoConverter {

    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(userDto.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    public User convertToEntity(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());

        return user;
    }
}
