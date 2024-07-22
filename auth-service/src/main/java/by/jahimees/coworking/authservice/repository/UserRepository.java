package by.jahimees.coworking.authservice.repository;

import by.jahimees.coworking.authservice.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> getUserByUsername(String username);
}
