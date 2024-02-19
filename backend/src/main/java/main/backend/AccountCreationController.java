package main.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountCreationController {
    @GetMapping("/register")
    public String register(Model model){
        return "register_page";
    }
}
