package by.jahimees.coworking.authservice.service;

import by.jahimees.coworking.authservice.data.User;
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

    private Optional<User> findUSerByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUSerByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
    }
}
