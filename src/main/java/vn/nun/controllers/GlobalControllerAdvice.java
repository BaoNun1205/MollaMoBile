package vn.nun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.nun.models.*;
import vn.nun.services.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final CartService cartService;
    private final CategoryService categoryService;
    private final AddressShippingService addressShippingService;
    private final UserService userService;
    private final WishListService wishListService;

    @Autowired
    public GlobalControllerAdvice(CartService cartService, CategoryService categoryService, AddressShippingService addressShippingService, UserService userService, WishListService wishListService) {
        this.cartService = cartService;
        this.categoryService = categoryService;
        this.addressShippingService = addressShippingService;
        this.userService = userService;
        this.wishListService = wishListService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {

        //hien thi cac danh muc
        List<Category> listCate = this.categoryService.getAll();
        model.addAttribute("listCate", listCate);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isAuthenticated", isAuthenticated);

        if (isAuthenticated) {

            //hien thi thong tin nguoi dung hien tai
            User user = userService.currentUser();
            model.addAttribute("currentUser", user);

            Cart cart = cartService.getCartForCurrentUser();

            List<CartItem> listCartItem = cart.getCartItems();

            double total = 0.00;
            if (listCartItem != null) {
                for (CartItem item : listCartItem) {
                    total += item.getCount() * item.getProduct().getPrice();
                }
            }

            // làm tròn total
            String formattedTotal = String.format("%.2f", total);

            model.addAttribute("total", formattedTotal); //tong tien gio hang
            model.addAttribute("cart", cart);

            WishList wishList = wishListService.getWishlistCurrentUser();

            model.addAttribute("wishlist", wishList);
        }
    }
}
