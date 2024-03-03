package webapp4.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoanRequestController {
    private final String[] loanTypes = {
            "Housing", "Education", "Medical", "Other"
    };
    private int amount;
    private int installments;
    private final float interestRate = 7.5f;
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
        return "loan_visualizer_page";
    }
    @GetMapping("/data")
    @ResponseBody
    public List<Map<String, Float>> getLoanData(@RequestParam int startIndex, @RequestParam int chunkSize) {
        List<Map<String, Float>> loanPayments = new ArrayList<>();
        float monthlyInterestRate = interestRate / 100 / 12;
        float remainingAmount = amount;
        for (int i = startIndex; i < startIndex + chunkSize; i++) {
            if (i >= installments) {
                break;
            }
            float interestPayment = remainingAmount * monthlyInterestRate;
            float principalPayment = amount / installments;
            Map<String, Float> payment = new HashMap<>();
            payment.put("period", (float) (i + 1));
            payment.put("principal", principalPayment);
            payment.put("interest", interestPayment);
            payment.put("remainingAmount", remainingAmount);
            loanPayments.add(payment);
            remainingAmount -= principalPayment;
        }
        return loanPayments;
    }
}
