package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/{id}")
    public String DetailProduct(Model model, @PathVariable("id") Integer id){
        Product product = productService.findById(id);
		model.addAttribute("product", product);
		return ("user/product");
    }

    @PostMapping("/add-cart")
    public ResponseEntity<?> AddCartItem(@RequestParam("productId") Integer productId,
                                      @RequestParam("count") Integer count){
        try {
            //Lat cart cua nguoi dung
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

            // Trả về phản hồi thành công
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e){
            e.printStackTrace();
            // Trả về phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

    @GetMapping ("/add-cart/{id}")
    public ResponseEntity<?> AddOneCartItem(@PathVariable("id") Integer productId){

        try {
            Cart cart = cartService.getCartForCurrentUser();

            Product product = productService.findById(productId);
            if (cartItemService.existProduct(cart, product)){
                CartItem cartItem = cartItemService.findByProduct(cart, product);
                cartItem.setCount(cartItem.getCount() + 1);
                cartItemService.update(cartItem);
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setCount(1);
                cartItemService.create(cartItem);
            }

            // Trả về phản hồi thành công
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e){
            e.printStackTrace();
            // Trả về phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
}
