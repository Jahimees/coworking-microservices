package by.jahimees.coworking.authservice.service;

import by.jahimees.coworking.authservice.data.Role;
import by.jahimees.coworking.authservice.exception.RoleNotFoundException;
import by.jahimees.coworking.authservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByName(String name) throws RoleNotFoundException {
        Optional<Role> roleOptional = roleRepository.findRoleByName(name);

        return roleOptional.orElseThrow(() -> new RoleNotFoundException("Role with name %s not found".formatted(name)));
    }

}
