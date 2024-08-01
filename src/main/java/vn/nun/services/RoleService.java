package vn.nun.services;

import vn.nun.models.Role;

public interface RoleService {
    Role findByName(String name);
    Role save(Role role);
}
