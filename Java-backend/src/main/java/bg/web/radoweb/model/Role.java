package bg.web.radoweb.model;

import bg.web.radoweb.model.Enum.UserRoles;
import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles name;

    public Role() {

    }

    public Long getId() {
        return id;
    }

    public Role setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRoles getName() {
        return name;
    }

    public Role setName(UserRoles name) {
        this.name = name;
        return this;
    }
}
