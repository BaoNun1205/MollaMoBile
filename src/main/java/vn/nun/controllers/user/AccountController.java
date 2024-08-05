package vn.nun.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.nun.models.AddressShipping;
import vn.nun.models.OrderItem;
import vn.nun.models.OrderPlaced;
import vn.nun.models.User;
import vn.nun.services.AddressShippingService;
import vn.nun.services.OrderPlacedService;
import vn.nun.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private OrderPlacedService orderPlacedService;
    @Autowired
    private AddressShippingService addressShippingService;
    @Autowired
    private UserService userService;
    @GetMapping
    public String showAccount(Model model, @ModelAttribute("currentUser") User currentUser){
        //lay dia chi giao hang cua nguoi dung
        AddressShipping addressShipping = addressShippingService.getAddressShippingForCurrentUser();

        //lay username nguoi dung hien tai
        String username = currentUser.getUserName();

        //lay danh sach item da order
        List<OrderPlaced> listOrder = userService.findByUserName(username).getOrderPlaceds();

        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderPlaced orderPlaced : listOrder){
            orderItemList.addAll(orderPlaced.getOrderItems());
        }

        //truyen du lieu sang view
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("addressShipping", addressShipping);
        model.addAttribute("listOrder", orderItemList);
        return "user/account";
    }
}
