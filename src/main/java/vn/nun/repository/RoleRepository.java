package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nun.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
