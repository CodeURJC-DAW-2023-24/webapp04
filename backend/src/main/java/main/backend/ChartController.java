package main.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {
    @GetMapping("/chart")
    public String transfer(Model model){
        return "chart_page";
    }
}
