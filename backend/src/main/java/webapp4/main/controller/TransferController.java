
package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp4.main.service.TransferService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TransferController {
    @Autowired
    private TransferService transferService;
    @GetMapping("/transfer")
    public String transfer(Model model, HttpServletRequest request){
        return "transfer_page";
    }
    @PostMapping("/transfer_processing")
    public String transferProcessing(Model model, HttpServletRequest request, @RequestParam String receiver_iban, @RequestParam String amount){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        transferService.addTransaction(username, receiver_iban, Integer.parseInt(amount));
        return "redirect:/profile";
    }
}