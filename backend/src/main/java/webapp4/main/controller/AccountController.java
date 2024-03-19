package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import webapp4.main.model.Account;
import webapp4.main.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    private List<Account> allAccounts;
    @ModelAttribute
    public void auxMethod(Model model){
        allAccounts = accountRepository.findAll();
    }
    @GetMapping("/account")
    public String account(Model model){
        return "account_page";
    }
    @GetMapping("/account_load")
    @ResponseBody
    public List<Account> accountManager(@RequestParam int startIndex, @RequestParam int chunkSize){
        if (startIndex + chunkSize < allAccounts.size()){
            return allAccounts.subList(startIndex, startIndex + chunkSize);
        }
        return new ArrayList<>();
    }
}

