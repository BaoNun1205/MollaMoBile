package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.nun.models.AddressShipping;
import vn.nun.models.User;
import vn.nun.services.AddressShippingService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/address-shipping")
public class AddressShippingController {
    @Autowired
    private AddressShippingService addressShippingService;
    @PostMapping("/save-address")
    public ResponseEntity<Void> saveAddress(
            @ModelAttribute("addressShipping") AddressShipping addressShipping,
            @RequestParam String recipientName,
            @RequestParam String address,
            @RequestParam String phone) {

        try {
            // Xử lý dữ liệu từ các tham số
            addressShipping.setRecipientName(recipientName);
            addressShipping.setAddress(address);
            addressShipping.setPhone(phone);

            addressShippingService.save(addressShipping);

            // Trả về phản hồi thành công
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
}
