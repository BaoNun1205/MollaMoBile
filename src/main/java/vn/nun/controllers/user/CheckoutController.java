package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nun.models.CartItem;
import vn.nun.models.Delivery;
import vn.nun.services.DeliveryService;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private DeliveryService deliveryService;
    @PostMapping
    public String checkout(@RequestParam("shippingMethodId") Integer shippingMethodId,
                           @ModelAttribute("listCartItem") List<CartItem> cartItemList,
                           Model model) {
        // Tìm đối tượng Delivery từ shippingMethodId
        Delivery selectedDelivery = deliveryService.findById(shippingMethodId);

        double cartTotal = 0.00;
        for (CartItem cartItem : cartItemList){
            cartTotal += (cartItem.getCount() * cartItem.getProduct().getPrice());
        }

        Double cartTotalFinal = cartTotal + selectedDelivery.getPrice();

        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("cartTotalFinal", cartTotalFinal);

        // Đưa đối tượng Delivery vào model để truyền tới view
        model.addAttribute("selectedDelivery", selectedDelivery);

        // Tiến hành xử lý thanh toán và chuyển hướng đến trang kết quả thanh toán
        return "user/checkout";
    }

    @GetMapping
    public String checkout(@ModelAttribute("listCartItem") List<CartItem> cartItemList, Model model){

        Delivery freeship = deliveryService.findById(1);

        double cartTotal = 0.00;
        for (CartItem cartItem : cartItemList){
            cartTotal += (cartItem.getCount() * cartItem.getProduct().getPrice());
        }

        Double cartTotalFinal = cartTotal + freeship.getPrice();

        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("cartTotalFinal", cartTotalFinal);

        // Đưa đối tượng Delivery vào model để truyền tới view
        model.addAttribute("selectedDelivery", freeship);

        // Tiến hành xử lý thanh toán và chuyển hướng đến trang kết quả thanh toán
        return "user/checkout";
    }
}
