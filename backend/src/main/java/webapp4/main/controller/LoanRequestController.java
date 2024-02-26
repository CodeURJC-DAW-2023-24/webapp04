package webapp4.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoanRequestController {
    @GetMapping("/loan_request")
    public String loanRequest(Model model){
        return "loan_request_page";
    }
}
