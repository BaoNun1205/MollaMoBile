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
    private AddressShippingService addressShippingService;
    @Autowired
    private OrderPlacedService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CartItemService cartItemService;
    @PostMapping
    public String checkout(@RequestParam("shippingMethodId") Integer shippingMethodId,
                           @ModelAttribute("cart") Cart cart,
                           Model model) {
        // Tìm đối tượng Delivery từ shippingMethodId
        Delivery selectedDelivery = deliveryService.findById(shippingMethodId);

        //truyen dia chi giao hang
        AddressShipping addressShipping = addressShippingService.getAddressShippingForCurrentUser();
        model.addAttribute("addressShipping", addressShipping);

        double cartTotal = 0.00;
        for (CartItem cartItem : cart.getCartItems()){
            cartTotal += (cartItem.getCount() * cartItem.getProduct().getPrice());
        }

        // làm tròn cartTotal
        String formattedCartTotal = String.format("%.2f", cartTotal);

        Double cartTotalFinal = cartTotal + selectedDelivery.getPrice();

        // làm tròn cartTotalFinal
        String formattedCartTotalFinal = String.format("%.2f", cartTotalFinal);

        model.addAttribute("cartTotal", formattedCartTotal);
        model.addAttribute("cartTotalFinal", formattedCartTotalFinal);

        model.addAttribute("selectedDelivery", selectedDelivery);

        return "user/checkout";
    }

    @GetMapping
    public String checkout(@ModelAttribute("cart") Cart cart, Model model){
        //truyen dia chi giao hang
        AddressShipping addressShipping = addressShippingService.getAddressShippingForCurrentUser();
        model.addAttribute("addressShipping", addressShipping);

        Delivery freeship = deliveryService.findById(1);

        double cartTotal = 0.00;
        for (CartItem cartItem : cart.getCartItems()){
            cartTotal += (cartItem.getCount() * cartItem.getProduct().getPrice());
        }

        // làm tròn cartTotal
        String formattedCartTotal = String.format("%.2f", cartTotal);

        Double cartTotalFinal = cartTotal + freeship.getPrice();

        // làm tròn cartTotalFinal
        String formattedCartTotalFinal = String.format("%.2f", cartTotalFinal);

        model.addAttribute("cartTotal", formattedCartTotal);
        model.addAttribute("cartTotalFinal", formattedCartTotalFinal);

        model.addAttribute("selectedDelivery", freeship);

        return "user/checkout";
    }

    @PostMapping("/place-order")
    public String placeOrder(@ModelAttribute("addressShipping") AddressShipping addressShipping,
                             @ModelAttribute("cart") Cart cart,
                             @ModelAttribute("currentUser") User user,
                             @RequestParam("deliveryId") Integer deliveryId,
                             @RequestParam(name = "isUpdateAddress", defaultValue = "false") Boolean isUpdateAddress,
                             @RequestParam("notes") String notes){

        if (isUpdateAddress){
            AddressShipping addressShippingCurrent = addressShippingService.getAddressShippingForCurrentUser();
            addressShippingCurrent.setRecipientName(addressShipping.getRecipientName());
            addressShippingCurrent.setAddress(addressShipping.getAddress());
            addressShippingCurrent.setPhone(addressShipping.getPhone());

            addressShippingService.save(addressShippingCurrent);
        }

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
        for (CartItem cartItem : cart.getCartItems()){
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
