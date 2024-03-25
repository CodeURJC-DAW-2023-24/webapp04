package webapp4.main.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileManagerController {

    @GetMapping("/profile_manager")
    public String password(Model model, HttpSession session){
        return "profile_manager_page";
    }

}
