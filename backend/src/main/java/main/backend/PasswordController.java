package main.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordController {
    @GetMapping("/password")
    public String password(Model model){
        return "password_page";
    }
}
