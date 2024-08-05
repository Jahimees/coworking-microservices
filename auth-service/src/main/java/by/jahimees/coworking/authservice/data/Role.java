package by.jahimees.coworking.authservice.data;

import by.jahimees.coworking.authservice.data.dto.RoleDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public Role(RoleDto roleDto) {
        this.id = roleDto.getId();
        this.name = roleDto.getName();
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
