package vn.nun.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logon")
public class LogonController {

    @GetMapping
    public String logon() {
        return ("admin/logon");
    }
}
