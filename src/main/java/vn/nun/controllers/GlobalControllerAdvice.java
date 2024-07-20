package vn.nun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.nun.models.*;
import vn.nun.services.AddressShippingService;
import vn.nun.services.CartService;
import vn.nun.services.CategoryService;
import vn.nun.services.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final CartService cartService;
    private final CategoryService categoryService;
    private final AddressShippingService addressShippingService;
    private final UserService userService;

    @Autowired
    public GlobalControllerAdvice(CartService cartService, CategoryService categoryService, AddressShippingService addressShippingService, UserService userService) {
        this.cartService = cartService;
        this.categoryService = categoryService;
        this.addressShippingService = addressShippingService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {

        List<Category> listCate = this.categoryService.getAll();
        model.addAttribute("listCate", listCate);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {

            User user = userService.currentUser();
            model.addAttribute("currentUser", user);

            Cart cart = cartService.getCartForCurrentUser();

            List<CartItem> listCartItem = cart.getCartItems();

            if (listCartItem != null){
                double total = 0.00;
                for (CartItem item : listCartItem) {
                    total += item.getCount() * item.getProduct().getPrice();
                }

                model.addAttribute("total", total); //tong tien gio hang
                model.addAttribute("listCartItem", listCartItem); //item trong gio hang
                model.addAttribute("cart", cart);
            }

            //truyen dia chi giao hang
            AddressShipping addressShipping = addressShippingService.getAddressShippingForCurrentUser();
            if (addressShipping != null){
                model.addAttribute("addressShipping", addressShipping);
            } else {
                AddressShipping newAddressShipping = new AddressShipping();
                newAddressShipping.setUser(user);
                model.addAttribute("addressShipping", newAddressShipping);
            }

        }
    }
}
