package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp4.main.model.Account;
import webapp4.main.model.UserData;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.UserDataRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Random;


import java.security.SecureRandom;
import java.util.Optional;

@Controller
public class AccountCreationController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    @GetMapping("/register")
    public String register(Model model){
        return "register_page";
    }
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @RequestMapping("/create_account")
    public String createAccount(Model model, @RequestParam String inputUser, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String inputPassword, @RequestParam String confirmPassword) {
        Optional<Account> accountOptional = accountRepository.findByNIP(inputUser);
        if (accountOptional.isPresent()){
            System.out.println("Account already exists");
            return "redirect:/register";
        } else {
            if (inputPassword.equals(confirmPassword)) {
                Account account = new Account();
                account.setNIP(inputUser);
                String iban = generateIBAN();
                account.setIBAN(iban);
                account.setName(firstName);
                account.setSurname(lastName);
                accountRepository.save(account);
                UserData userData = new UserData();
                userData.setUsername(inputUser);
                PasswordEncoder passwordEncoder = passwordEncoder();
                userData.setPassword(passwordEncoder.encode(inputPassword));
                userData.setRole("USER");
                userDataRepository.save(userData);
                return "redirect:/profile";
            } else {
                return "redirect:/register_page";
            }
        }
    }
    private String generateIBAN() {
        String countryCode = "ES";
        int ibanLength = 24;
        StringBuilder ibanBuilder = new StringBuilder(countryCode);
        Random random = new Random();
        for (int i = 0; i < ibanLength - countryCode.length(); i++) {
            if ((ibanLength + 2 - i) % 4 == 0){
                ibanBuilder.append(" ");
            }
            ibanBuilder.append(random.nextInt(10));
        }
        return ibanBuilder.toString();
    }
}
