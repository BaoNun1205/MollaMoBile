package vn.nun.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.nun.models.Cart;
import vn.nun.models.CustomUserDetail;
import vn.nun.models.User;
import vn.nun.repository.CartRepository;
import vn.nun.services.CartService;
import vn.nun.services.UserService;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;

    @Override
    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart findByUser(User user) {
        return cartRepository.findCartByUser(user);
    }

    @Override
    public Cart getCartForCurrentUser() {
        User user = userService.currentUser();
        return findByUser(user);
    }
}
