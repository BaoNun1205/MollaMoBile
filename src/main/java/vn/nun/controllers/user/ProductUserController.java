package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nun.models.*;
import vn.nun.services.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductUserController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public String DetailProduct(Model model, @PathVariable("id") Integer id){
        Product product = productService.findById(id);
        List<Category> listCate = this.categoryService.getAll();
        model.addAttribute("listCate", listCate);
		model.addAttribute("product", product);
//        Cart cart = cartService.getCartForCurrentUser();
//
//        if (cart != null){
//            List<CartItem> listCartItem = cart.getCartItems();
//            model.addAttribute("listCartItem", listCartItem);
//        }
		return ("user/product");
    }

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/add-cart")
    public String AddCart(Model model, @RequestParam("productId") Integer productId, @RequestParam("count") Integer count,  @ModelAttribute("isAuthenticated") boolean isAuthenticated){
        if (!isAuthenticated) {
            return "redirect:/login";
        }

        Cart cart = cartService.getCartForCurrentUser();

        Product product = productService.findById(productId);
        if (cartItemService.existProduct(cart, product)){
            CartItem cartItem = cartItemService.findByProduct(cart, product);
            cartItem.setCount(cartItem.getCount() + count);
            cartItemService.update(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setCount(count);
            cartItemService.create(cartItem);
        }

        // Redirect to product details page after adding to cart
        return "redirect:/product/" + productId;
    }
}
