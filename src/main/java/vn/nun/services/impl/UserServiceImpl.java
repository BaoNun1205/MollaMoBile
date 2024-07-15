package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.nun.models.User;
import vn.nun.repository.UserRepository;
import vn.nun.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

}