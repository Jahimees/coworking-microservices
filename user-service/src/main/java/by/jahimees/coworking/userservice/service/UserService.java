package by.jahimees.coworking.userservice.service;

import by.jahimees.coworking.userservice.data.User;
import by.jahimees.coworking.userservice.exception.EmailAlreadyExistsException;
import by.jahimees.coworking.userservice.exception.NotEnoughCreationDataException;
import by.jahimees.coworking.userservice.exception.UserNotFoundException;
import by.jahimees.coworking.userservice.exception.UsernameAlreadyExistsException;
import by.jahimees.coworking.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static by.jahimees.coworking.userservice.constants.MessagingConstants.COMMITED_DB_STATUS;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(User user) throws NotEnoughCreationDataException, UsernameAlreadyExistsException,
            EmailAlreadyExistsException {
        if (user == null
                || user.getId() == null || user.getId() == 0 || user.getId() == -1
                || user.getUsername() == null || user.getUsername().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new NotEnoughCreationDataException("User data hasn't enough data for registration");
        }

        checkUserExistence(user);

        return userRepository.saveAndFlush(user);
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
    }

    public Optional<User> findByRequestId(String requestId) {
        return userRepository.getUserByRequestId(requestId);
    }

    public void commit(String requestId) throws UserNotFoundException {
        Optional<User> userOptional = findByRequestId(requestId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found with requestId %s".formatted(requestId));
        }

        User user = userOptional.get();
        user.setStatus(COMMITED_DB_STATUS);

        userRepository.save(user);
    }
}
