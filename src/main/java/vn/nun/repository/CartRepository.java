package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nun.models.Cart;
import vn.nun.models.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByUser(User user);
}
