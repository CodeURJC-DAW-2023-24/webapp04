package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.service.TransferService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@CrossOrigin(origins = "*")
@RestController
public class RestTransferController {
    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;


    @Operation  (summary = "Get a Transfer by ID")
    @ApiResponse(
            responseCode = "200",
            description = "Found the Transfer",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class))
    )
    @ApiResponse(responseCode = "404", description = "Form not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
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

    @Operation (summary = "Get all transfers")
    @ApiResponse(
            responseCode = "200",
            description = "Found the form",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class))
    )
    @ApiResponse(responseCode = "404", description = "Transfer Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/transfers")
    public ResponseEntity<Collection<Transfer>> getAllTransfer(){
        Collection<Transfer> allTransfers = transferRepository.findAll();
        return ResponseEntity.ok(allTransfers);
    }

   @GetMapping("/api/transfers/paged")
    public ResponseEntity<Page<Transfer>> getAllTransfersPaged(Pageable page) {
        Page<Transfer> transfers = transferRepository.findAll(page);
        return ResponseEntity.ok(transfers);
    }


    @Operation (summary = "Get all user transfers")
    @ApiResponse(
            responseCode = "200",
            description = "Found the form",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class))
    )
    @ApiResponse(responseCode = "404", description = "Transfer Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/accounts/{id}/transfers")
    public ResponseEntity<Collection<Transfer>> getAllUserTransfer(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (id.equals(username)) {
            Optional<Account> accountOptional = accountRepository.findByNIP(id);
            String accountIBAN = accountOptional.get().getIBAN();
            Collection<Transfer> userTransfers = transferRepository.findBySenderOrReceiverContaining(accountIBAN);
            return ResponseEntity.ok(userTransfers);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation (summary = "Make an user transfer")
    @ApiResponse(
            responseCode = "200",
            description = "transfer success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class))
    )
    @ApiResponse(responseCode = "404", description = "Transfer Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @PostMapping("/api/accounts/{id}/transfers")
    public ResponseEntity<?> createTransfer(Model model, HttpServletRequest request, @RequestBody Transfer transferJSON, @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (id.equals(username)) {
            String receiver_iban = transferJSON.getReceiverIBAN();
            int amount = transferJSON.getAmount();
            Object createTransfer = transferService.addTransaction(username, receiver_iban, amount);
            if (createTransfer instanceof Transfer) {
                Transfer transfer = (Transfer) createTransfer;
                URI location = fromCurrentRequest().path("/{id}")
                        .buildAndExpand(transfer.getTransfer_id()).toUri();
                return ResponseEntity.created(location).body(transfer);
            } else {
                String errorMessage = (String) createTransfer;
                if (errorMessage.equals("user not exists")) {
                    return ResponseEntity.badRequest().body("User not exists");
                }
                if (errorMessage.equals("not enough balance")) {
                    return ResponseEntity.badRequest().body("there is not enough balance");
                }
                if (errorMessage.equals("negative transfer")) {
                    return ResponseEntity.badRequest().body("you can't create negative transfers");
                }
                return null;
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
