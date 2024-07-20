package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nun.models.*;
import vn.nun.services.*;

import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private OrderPlacedService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CartItemService cartItemService;
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

        OrderPlaced order = new OrderPlaced();
        model.addAttribute("order", order);

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

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute("addressShipping") AddressShipping addressShipping,
                             @ModelAttribute("listCartItem") List<CartItem> cartItemList,
                             @ModelAttribute("currentUser") User user,
                             @RequestParam("deliveryId") Integer deliveryId,
                             @RequestParam("notes") String notes){

        //Thong tin don dat hang
        OrderPlaced order = OrderPlaced.builder()
                .user(user)
                .delivery(deliveryService.findById(deliveryId))
                .recipientName(addressShipping.getRecipientName())
                .address(addressShipping.getAddress())
                .phone(addressShipping.getPhone())
                .notes(notes)
                .status("Placed Order")
                .build();

        order = orderService.create(order);

        //cac item trong don dat hang duoc map tu gio hang
        for (CartItem cartItem : cartItemList){
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .count(cartItem.getCount())
                    .build();

            orderItemService.save(orderItem);
            cartItemService.delete(cartItem.getId());// xoa item khoi cart
        }

        return "redirect:/account";
    }
}
