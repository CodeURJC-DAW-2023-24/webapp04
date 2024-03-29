package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.service.TransferService;
import webapp4.main.service.UserDataService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;


import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
public class RestTransferController {


    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferRepository transferRepository;


    @PostMapping("/api/transfers")
    public ResponseEntity<?> createTransfer(Model model, HttpServletRequest request,@RequestParam String sender_user, @RequestParam String receiver_iban, @RequestParam String amount) {
        Object createTransfer = transferService.addTransaction(sender_user, receiver_iban, Integer.parseInt(amount));
        if (createTransfer instanceof Transfer) {
            Transfer transfer = (Transfer) createTransfer;
            URI location = fromCurrentRequest().path("/{id}")
                    .buildAndExpand(transfer.getTransfer_id()).toUri();
            return ResponseEntity.created(location).body(transfer);
        } else {
            String errorMessage = (String) createTransfer;
            if (errorMessage.equals("user not exists")){
                return  ResponseEntity.badRequest().body("User not exists");
            }
            return null;
        }
    }

    @GetMapping("/api/transfers/{id}")
    public ResponseEntity<Transfer> getTransfer(@PathVariable Long id){
        Optional<Transfer> transferOptional = transferRepository.findById(id);
        Transfer transfer = transferOptional.get();
        if (transfer != null) {
            return ResponseEntity.ok(transfer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
