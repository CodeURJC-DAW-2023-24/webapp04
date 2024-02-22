package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.transferDataUtils.ProcessedTransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferRepository transferRepository;
    @PostMapping("/profile")
    public String profile(Model model, @RequestParam String inputUser, @RequestParam String inputPassword) {
        Optional<Account> accountOptional = accountRepository.findByNIP(inputUser);
        if (accountOptional.isPresent()){
            // TODO Validate user's password before allowing access to their account
            System.out.println("HOLA " + inputUser);
            // Add to mustache all transfer data associated with the account
            String accountIBAN = accountOptional.get().getIBAN();
            List<Transfer> transferList = transferRepository.findBySenderOrReceiverContaining(accountIBAN);
            ArrayList<ProcessedTransfer> processedTransferList = new ArrayList<>();
            for (Transfer transfer: transferList){
                processedTransferList.add(new ProcessedTransfer(transfer, accountIBAN));
            }
            model.addAttribute("client_iban", accountIBAN);
            model.addAttribute("client_name", accountOptional.get().getName());
            model.addAttribute("transfer_list", processedTransferList);
            return "profile_page";
        } else {
            System.out.println("ESE USUARIO NO EXISTE");
            return "login_page";
        }
    }
}