package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nun.models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
