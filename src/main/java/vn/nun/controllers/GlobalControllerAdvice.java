package vn.nun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.nun.models.Cart;
import vn.nun.models.CartItem;
import vn.nun.models.Category;
import vn.nun.services.CartService;
import vn.nun.services.CategoryService;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final CartService cartService;
    private final CategoryService categoryService;

    @Autowired
    public GlobalControllerAdvice(CartService cartService, CategoryService categoryService) {
        this.cartService = cartService;
        this.categoryService = categoryService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {

        List<Category> listCate = this.categoryService.getAll();
        model.addAttribute("listCate", listCate);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {
            Cart cart = cartService.getCartForCurrentUser();
            if (cart != null) {
                List<CartItem> listCartItem = cart.getCartItems();

                double total = 0;
                for (CartItem item : listCartItem) {
                    total += item.getCount() * item.getProduct().getPrice();
                }

                model.addAttribute("cart", cart);
                model.addAttribute("total", total);
                model.addAttribute("listCartItem", listCartItem);
            }
        }
    }
}
