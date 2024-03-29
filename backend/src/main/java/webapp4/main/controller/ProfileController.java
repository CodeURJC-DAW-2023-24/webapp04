
package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.service.AccountService;
import webapp4.main.service.TransferService;
import webapp4.main.transferDataUtils.ProcessedTransfer;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class ProfileController {
    private ArrayList<ProcessedTransfer> processedTransferList;
    private String clientNIP;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private TransferService transferService;
    @Autowired
    private AccountService accountService;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            Optional<Account> accountOptional = accountRepository.findByNIP(principal.getName());
            if (accountOptional.isPresent()) {
                // --- Setting IBAN ---
                String accountIBAN = accountOptional.get().getIBAN();
                model.addAttribute("client_iban", accountIBAN);
                // --- Setting client's name ---
                model.addAttribute("client_name", accountOptional.get().getName());
                // --- Setting client's NIP ---
                clientNIP = accountOptional.get().getNIP();
                // --- Setting transfer list ---
                List<Transfer> transferList = transferRepository.findBySenderOrReceiverContaining(accountIBAN);
                processedTransferList = new ArrayList<>();
                int balance = 0;
                for (Transfer transfer : transferList) {
                    ProcessedTransfer processedTransfer = new ProcessedTransfer(transfer, accountIBAN);
                    processedTransferList.add(processedTransfer);
                    balance += processedTransfer.getAmount();
                }
                // --- Setting client's balance ---
                model.addAttribute("client_balance", balance);
            } else {
                System.out.println("USER NOT FOUND");
            }
        } else {
            model.addAttribute("logged", false);
        }
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        return "profile_page";
    }

    @GetMapping("/profile_data")
    @ResponseBody
    public List<ProcessedTransfer> getTransferData(@RequestParam int startIndex, @RequestParam int chunkSize) {
        transferService.getProcessedTransfers(accountService.getUserByNIP(clientNIP).get().getIBAN());
        if (startIndex + chunkSize <= processedTransferList.size()){
            return transferService.getProcessedTransfers(accountService.getUserByNIP(clientNIP).get().getIBAN()).subList(startIndex, startIndex + chunkSize);
        } else if (startIndex + chunkSize > processedTransferList.size()){
            return transferService.getProcessedTransfers(accountService.getUserByNIP(clientNIP).get().getIBAN()).subList(startIndex, processedTransferList.size());
        }
        return new ArrayList<>();
    }

    @GetMapping("/image_loader")
    @ResponseBody
    public ResponseEntity<byte[]> downloadImage() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(accountService.getProfilePicBytes(clientNIP));
    }
}
