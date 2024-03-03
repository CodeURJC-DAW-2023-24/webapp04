package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp4.main.model.Account;
import webapp4.main.repository.AccountRepository;


import java.util.Optional;

@Controller
public class AccountCreationController {

    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/register")
    public String register(Model model){
        return "register_page";
    }

    @RequestMapping("/create_account")
    public String createAccount(Model model, @RequestParam String inputUser, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String inputPassword, @RequestParam String confirmPassword) {
        Optional<Account> accountOptional = accountRepository.findByNIP(inputUser);
        if (accountOptional.isPresent()){
            System.out.println("Account already exists");
            return "register_page";
        } else {
            if (inputPassword.equals(confirmPassword)) {
                Account account = new Account();
                account.setNIP(inputUser);
                account.setIBAN("ES12 3456 7890 1234 5678 9012");
                account.setName(firstName);
                account.setSurname(lastName);
                accountRepository.save(account);
                return "profile_page";
            } else {
                return "register_page";
            }
        }
    }
}
