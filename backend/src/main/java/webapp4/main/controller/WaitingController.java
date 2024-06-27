package webapp4.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WaitingController {
    @GetMapping("/waiting")
    public String waiting(Model model){
        return "waiting_page";
    }
}
