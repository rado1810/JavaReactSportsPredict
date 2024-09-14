package bg.web.radoweb.repository;

import bg.web.radoweb.model.Enum.UserRoles;
import bg.web.radoweb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role,Long> {
    Role findByName(UserRoles userRoles);
}
