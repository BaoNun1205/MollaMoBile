package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nun.dto.CartItemDTO;
import vn.nun.models.CartItem;
import vn.nun.models.Delivery;
import vn.nun.services.CartItemService;
import vn.nun.services.DeliveryService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/view-cart")
public class CartController {
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private CartItemService cartItemService;
    @GetMapping
    public String ViewCart(Model model){
        List<Delivery> deliveries = deliveryService.getAll();
        model.addAttribute("deliveries", deliveries);
        return "user/cart";
    }

    @PostMapping("/update-cart")
    public String updateCart(@RequestParam List<Integer> itemId,
                             @RequestParam List<Integer> quantity) {
        if (itemId.size() != quantity.size()) {
            return "redirect:/view-cart";
        }

        for (int i = 0; i < itemId.size(); i++) {
            Integer itemIdValue = itemId.get(i);
            Integer quantityValue = quantity.get(i);

            CartItem cartItem = cartItemService.findById(itemIdValue);
            if (cartItem != null) {
                cartItem.setCount(quantityValue);
                cartItemService.update(cartItem); // Cập nhật lại số lượng trong cơ sở dữ liệu
            }
        }
        return "redirect:/view-cart";
    }


}
