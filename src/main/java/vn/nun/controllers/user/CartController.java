package vn.nun.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view-cart")
public class CartController {
    @GetMapping
    public String ViewCart(){
        return "user/cart";
    }
}
