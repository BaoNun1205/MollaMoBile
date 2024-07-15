package vn.nun.services;

import vn.nun.models.Cart;
import vn.nun.models.User;

public interface CartService {
    Cart findByUser(User user);
    Cart getCartForCurrentUser();
}
