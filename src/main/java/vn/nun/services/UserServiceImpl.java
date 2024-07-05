package vn.nun.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.nun.models.User;
import vn.nun.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Override
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

}
