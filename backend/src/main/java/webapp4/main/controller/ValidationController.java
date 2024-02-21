package webapp4.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ValidationController {
    @GetMapping("/validation")
    public String validation(Model model){
        return "validation_page";
    }
}
