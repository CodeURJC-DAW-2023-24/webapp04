package webapp4.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileForwardController {
    @GetMapping("/profile_forward")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/profile_manager";
        }
        return "redirect:/profile";
    }
}
