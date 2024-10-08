package by.jahimees.coworking.authservice.service;

import by.jahimees.coworking.authservice.data.User;
import by.jahimees.coworking.authservice.data.message.ServiceMessage;
import by.jahimees.coworking.authservice.exception.*;
import by.jahimees.coworking.authservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static by.jahimees.coworking.authservice.constants.MessagingConstants.COMMITED_DB_STATUS;
import static by.jahimees.coworking.authservice.util.Constants.ROLE_USER;

@Data
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Transactional
    public User create(User user) throws UsernameAlreadyExistsException, NotEnoughRegistrationData,
            EmailAlreadyExistsException, RoleNotFoundException {
        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new NotEnoughRegistrationData("User data hasn't enough data for registration");
        }

        checkUserExistence(user);

        user.setRoles(List.of(roleService.findByName(ROLE_USER)));

        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public void commit(String requestId) {
        Optional<User> userOptional = findUserByRequestId(requestId);

        if (userOptional.isEmpty()) {
            //log error return
            return;
        }

        User user = userOptional.get();
        user.setStatus(COMMITED_DB_STATUS);


        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }

    public Optional<User> findUserByRequestId(String requestId) {
        return userRepository.getUserByRequestId(requestId);
    }

    private Optional<User> findUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
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

}
