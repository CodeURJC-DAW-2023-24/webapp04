package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.transferDataUtils.ProcessedTransfer;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferRepository transferRepository;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<Account> accountOptional = accountRepository.findByNIP(principal.getName());
            if (accountOptional.isPresent()) {
                String accountIBAN = accountOptional.get().getIBAN();
                List<Transfer> transferList = transferRepository.findBySenderOrReceiverContaining(accountIBAN);
                ArrayList<ProcessedTransfer> processedTransferList = new ArrayList<>();
                for (Transfer transfer : transferList) {
                    processedTransferList.add(new ProcessedTransfer(transfer, accountIBAN));
                }
                model.addAttribute("client_iban", accountIBAN);
                model.addAttribute("client_name", accountOptional.get().getName());
                model.addAttribute("transfer_list", processedTransferList);
            } else {
                System.out.println("ESE USUARIO NO EXISTE");
            }
            /*
            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));
            */
        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        return "profile_page";
    }
}
