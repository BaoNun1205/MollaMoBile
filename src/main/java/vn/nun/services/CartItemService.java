package vn.nun.services;

import vn.nun.models.Cart;
import vn.nun.models.CartItem;
import vn.nun.models.Product;

public interface CartItemService {
    CartItem findById(Integer id);
    boolean create(CartItem cartItem);
    boolean update(CartItem cartItem);
    boolean delete(Integer id);

    boolean existProduct(Cart cart, Product product);
    CartItem findByProduct(Cart cart, Product product);
}
