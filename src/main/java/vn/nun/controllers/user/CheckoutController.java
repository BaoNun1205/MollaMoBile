package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.nun.models.*;
import vn.nun.services.*;

import java.util.Collections;
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
    @Autowired
    private ProductService productService;
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
    public ResponseEntity<?> placeOrder(@ModelAttribute("addressShipping") AddressShipping addressShipping,
                                     @ModelAttribute("cart") Cart cart,
                                     @ModelAttribute("currentUser") User user,
                                     @RequestParam("deliveryId") Integer deliveryId,
                                     @RequestParam(name = "isUpdateAddress", defaultValue = "false") Boolean isUpdateAddress,
                                     @RequestParam("notes") String notes){
        try {

            //kiem tra so luong san pham con lai cua moi cartItem con du khong
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();

                if (product.getQuantity() < cartItem.getCount()) {
                    // Trả về phản hồi lỗi
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(Collections.singletonMap("message", "Not enough stock for product " + product.getProductName()));
                }
            }

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
                Product product = cartItem.getProduct();

                //cap nhat so luong con lai cua san pham
                product.setQuantity(product.getQuantity() - cartItem.getCount()); //giam so luong san pham con lai
                product.setSold(product.getSold() + cartItem.getCount()); //tang so luong da ban
                productService.update(product); //cap nhat trong csdl

                //tao orderItem
                OrderItem orderItem = OrderItem.builder()
                        .order(order)
                        .product(cartItem.getProduct())
                        .count(cartItem.getCount())
                        .build();
                orderItemService.save(orderItem);

                cartItemService.delete(cartItem.getId());// xoa item khoi cart
            }

            // Trả về phản hồi thành công
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e){
            e.printStackTrace();
            // Trả về phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while placing the order"); // 500 Internal Server Error
        }
    }
}
