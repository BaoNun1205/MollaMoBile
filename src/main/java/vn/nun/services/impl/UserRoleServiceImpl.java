package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nun.models.UserRole;
import vn.nun.repository.UserRoleRepository;
import vn.nun.services.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Override
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
}
