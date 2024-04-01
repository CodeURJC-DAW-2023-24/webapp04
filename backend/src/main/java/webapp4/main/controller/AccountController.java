package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import webapp4.main.model.Account;
import webapp4.main.model.AccountDTO;
import webapp4.main.repository.AccountRepository;
import webapp4.main.service.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    private List<Account> allAccounts;
    @Autowired
    private AccountService accountService;

    @ModelAttribute
    public void auxMethod(Model model) {
        allAccounts = accountRepository.findAll();
    }

    @GetMapping("/account")
    public String account(Model model) {
        return "account_page";
    }

    @GetMapping("/account_load")
    @ResponseBody
    public List<AccountDTO> accountManager(@RequestParam int startIndex, @RequestParam int chunkSize) {
        int endIndex = Math.min(startIndex + chunkSize, allAccounts.size());
        return allAccounts.subList(startIndex, endIndex).stream().map(account -> {
            AccountDTO dto = new AccountDTO();
            dto.setNip(account.getNIP());
            dto.setIban(account.getIBAN());
            dto.setName(account.getName());
            dto.setSurname(account.getSurname());
            dto.setImageBase64(accountService.getProfilePicBase64(account.getNIP()));
            return dto;
        }).collect(Collectors.toList());
    }
}

