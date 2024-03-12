package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp4.main.service.UserDataService;


@Controller
public class AccountCreationController {
    @Autowired
    private UserDataService userDataService;
    @GetMapping("/register")
    public String register(Model model){
        return "register_page";
    }

    @RequestMapping("/create_account")
    public String createAccount(Model model, @RequestParam String inputUser, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String inputPassword, @RequestParam String confirmPassword) {
        if (inputPassword.equals(confirmPassword) && userDataService.registerUser(inputUser, firstName, lastName, inputPassword)){
            return "redirect:/profile";
        }
        return "redirect:/register_page";
    }
}
