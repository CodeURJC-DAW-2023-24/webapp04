package webapp4.main.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webapp4.main.service.LoanService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class LoanRequestController {
    private final String[] loanTypes = {
            "Housing", "Education", "Medical", "Other"
    };
    private int amount;
    private int installments;
    private static final float interestRate = 7.5f;
    @Autowired
    private LoanService loanService;
    @GetMapping("/loan_request")
    public String loanRequest(Model model, HttpSession session){
        return "loan_request_page";
    }
    @PostMapping("/loan_visualizer")
    public String loanVisualizer(Model model, HttpSession session, @RequestParam String inputAmount, @RequestParam String inputExpenseType, @RequestParam String inputInstallments){
        amount = Integer.parseInt(inputAmount);
        installments = Integer.parseInt(inputInstallments);
        model.addAttribute("reason", loanTypes[Integer.parseInt(inputExpenseType)]);
        model.addAttribute("amount", amount);
        model.addAttribute("interest", interestRate);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        loanService.addLoan(username, amount, installments);
        return "loan_visualizer_page";
    }
    @GetMapping("/data")
    @ResponseBody
    public List<Map<String, Float>> getLoanData(@RequestParam int startIndex, @RequestParam int chunkSize) {
        return loanService.getLoanInfo(amount, installments, startIndex, chunkSize);
    }
}
