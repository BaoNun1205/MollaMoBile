package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import vn.nun.models.CustomUserDetail;
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

	@Override
	public User currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}
		CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
		return userDetails.getUser();
	}

}
