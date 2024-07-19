package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.nun.dto.AddressShippingDTO;
import vn.nun.models.AddressShipping;
import vn.nun.models.User;
import vn.nun.services.AddressShippingService;
import vn.nun.services.UserService;

@Controller
@RequestMapping("/address-shipping")
public class AddressShippingController {
    @Autowired
    private AddressShippingService addressShippingService;
    @Autowired
    private UserService userService;
    @PostMapping("/save-address")
    public String save(@ModelAttribute("addressShipping") AddressShipping addressShipping){
        addressShipping.setUser(userService.currentUser());
        addressShippingService.save(addressShipping);
        return "redirect:/account";
    }
}
