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

            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));

        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        String inputUser = (String) session.getAttribute("username");
        String inputPassword = (String) session.getAttribute("password");
        System.out.println("HOLA GUAPO");
        if (inputUser == null || inputPassword == null) {
            System.out.println("O EL USUARIO O LA CONTRASEÑA SON NULOS LOLOLOL");
        }

        Optional<Account> accountOptional = accountRepository.findByNIP(inputUser);
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
            return "profile_page";
        } else {
            System.out.println("ESE USUARIO NO EXISTE");
            return "login_page";
        }
    }
}
