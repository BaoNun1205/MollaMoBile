package vn.nun.services;

import vn.nun.models.User;

public interface UserService {
	User findByUserName(String username);
	User currentUser();
	User save(User user);
	Boolean delete(String username);
}
