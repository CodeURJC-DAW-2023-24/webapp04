package main.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransferController {
    @GetMapping("/transfer")
    public String transfer(Model model){
        return "transfer_page";
    }
}
