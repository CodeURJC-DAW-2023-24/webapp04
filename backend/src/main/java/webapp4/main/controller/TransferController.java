package webapp4.main.controller;

import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class TransferController {
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;
    @GetMapping("/transfer")
    public String transfer(Model model, HttpServletRequest request){
        return "transfer_page";
    }
    @PostMapping("/transfer_processing")
    public String transferProcessing(Model model, HttpServletRequest request, @RequestParam String receiver_iban, @RequestParam String amount){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> accountOptional = accountRepository.findByNIP(username);
        if (accountOptional.isPresent()){
            String accountIBAN = accountOptional.get().getIBAN();
            // Adding to the DB the transaction
            Transfer transfer = new Transfer();
            transfer.setSenderIBAN(accountIBAN);
            transfer.setReceiverIBAN(receiver_iban);
            transfer.setAmount(Integer.parseInt(amount));
            transfer.setTransferType("transfer");
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");
            String formattedDateTime = currentDateTime.format(formatter);
            transfer.setDate(formattedDateTime);
            transferRepository.save(transfer);
        }

        return "redirect:/profile";
    }
}
