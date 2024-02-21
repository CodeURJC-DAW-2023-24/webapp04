package webapp4.main.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String profile(Model model) {
        //model.addAttribute("name", "World");
        return "profile_page";
    }
}