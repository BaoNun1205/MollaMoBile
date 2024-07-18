package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.nun.dto.AddressShippingDTO;
import vn.nun.models.AddressShipping;
import vn.nun.models.Product;
import vn.nun.services.AddressShippingService;

@Controller
public class AddressController {
    @Autowired
    private AddressShippingService addressShippingService;
    @PostMapping("/save-address")
    public String save(@ModelAttribute("addressShipping") AddressShippingDTO addressShippingDTO){
        AddressShipping addressShipping = addressShippingService.findById(addressShippingDTO.getId());
        addressShippingService.create(addressShipping);
        return "redirect:/account#tab-address";
    }
}
