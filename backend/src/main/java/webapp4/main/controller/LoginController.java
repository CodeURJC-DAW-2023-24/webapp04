package webapp4.main.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
        return "login_page";
    }
    @RequestMapping("/login_error")
    public String loginError() {
        return "login_error_page";
    }
}
