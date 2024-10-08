package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	@Override
	public User save(User user) {
		//ma hoa password
		user.setPassWord(new BCryptPasswordEncoder().encode(user.getPassWord()));
		return userRepository.save(user);
	}

	@Override
	public Boolean delete(String username) {
		try {
			userRepository.delete(findByUserName(username));
			return true;
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

}
