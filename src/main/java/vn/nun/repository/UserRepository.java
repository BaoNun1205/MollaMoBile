package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.nun.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String userName);
}
