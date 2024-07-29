package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.nun.models.User;
import vn.nun.models.WishList;

public interface WishListRepository extends JpaRepository<WishList, Integer> {
    WishList findByUser(User user);
}
