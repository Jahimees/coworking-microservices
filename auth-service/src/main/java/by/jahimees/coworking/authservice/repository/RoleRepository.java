package by.jahimees.coworking.authservice.repository;

import by.jahimees.coworking.authservice.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByName(String name);
}
