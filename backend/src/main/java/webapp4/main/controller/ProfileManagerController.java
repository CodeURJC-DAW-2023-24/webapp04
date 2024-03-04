package webapp4.main.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileManagerController {
    @GetMapping("/profile_manager")
    public String password(Model model){
        return "profile_manager";
    }
}
