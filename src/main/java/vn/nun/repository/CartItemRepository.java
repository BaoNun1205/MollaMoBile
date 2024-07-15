package vn.nun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.nun.models.Cart;
import vn.nun.models.CartItem;
import vn.nun.models.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    boolean existsByCartAndProduct(Cart cart, Product product);
    CartItem getCartItemByCartAndProduct(Cart cart, Product product);
}
