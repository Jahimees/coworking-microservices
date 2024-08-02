package by.jahimees.coworking.authservice.service;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.authservice.exception.NotEnoughRegistrationData;
import by.jahimees.coworking.authservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.authservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private Optional<User> findUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Transactional
    public User createUser(User user) throws UsernameAlreadyExistsException, NotEnoughRegistrationData,
            EmailAlreadyExistsException {
        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new NotEnoughRegistrationData("User data hasn't enough data for registration");
        }

        checkUserExistence(user);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    private void checkUserExistence(User user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        Optional<User> userOptional = userRepository.getUserByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            throw new UsernameAlreadyExistsException("User with this username already exists");
        }

        userOptional = userRepository.getUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new EmailAlreadyExistsException("User with this email already exists");
        }

        //TODO broker check in user service
    }
}
