package by.jahimees.coworking.authservice.repository;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.data.dto.UserDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    UserDto createUser(User user);
}
