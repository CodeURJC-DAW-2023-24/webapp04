package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.model.TransferDTO;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.service.TransferService;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
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
    @Operation(summary = "Get transfers for admin")
    @ApiResponse(
            responseCode = "200",
            description = "Found the form",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class))
    )
    @ApiResponse(responseCode = "404", description = "Transfer Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/transferslist")
    public ResponseEntity<Page<Transfer>> getTransfersForAdmin(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Pageable paging = PageRequest.of(page, size);
        Page<Transfer> allTransfers = transferRepository.findAll(paging);
        return ResponseEntity.ok(allTransfers);
    }
    @Operation(summary = "Get transfers for user")
    @ApiResponse(
            responseCode = "200",
            description = "Found the form",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class))
    )
    @ApiResponse(responseCode = "404", description = "Transfer Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/transfers")
    public ResponseEntity<Page<Transfer>> getTransfersBasedOnRole(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        Pageable paging = PageRequest.of(page, size);

        Optional<Account> accountOptional = accountRepository.findByNIP(authentication.getName());
        if (accountOptional.isPresent()) {
            String accountIBAN = accountOptional.get().getIBAN();
            Page<Transfer> userTransfers = transferRepository.findBySenderOrReceiverContainingPaged(accountIBAN, paging);
            return ResponseEntity.ok(userTransfers);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
    @PostMapping("/api/transfer")
    public ResponseEntity<?> createTransfer(HttpServletRequest request, @RequestBody TransferDTO transferJSON) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
    
            String receiver_iban = this.formatIBAN(transferJSON.getReceiverIBAN());
            int amount = transferJSON.getAmount();
    
            if (receiver_iban == null || receiver_iban.isEmpty()) {
                return ResponseEntity.badRequest().body("Receiver IBAN cannot be null or empty");
            }
    
            if (amount <= 0) {
                return ResponseEntity.badRequest().body("Amount must be greater than zero");
            }
    
            Optional<Account> accountOptional = accountRepository.findByNIP(username);
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                if (account.getIBAN() != null) {
                    Object createTransfer = transferService.addTransaction(username, receiver_iban, amount);
                    if (createTransfer instanceof Transfer) {
                        Transfer transfer = (Transfer) createTransfer;
                        URI location = fromCurrentRequest().path("/{id}")
                                .buildAndExpand(transfer.getTransfer_id()).toUri();
                        return ResponseEntity.created(location).body(transfer);
                    } else {
                        String errorMessage = (String) createTransfer;
                        switch (errorMessage) {
                            case "user not exists":
                                return ResponseEntity.badRequest().body("User not exists");
                            case "not enough balance":
                                return ResponseEntity.badRequest().body("There is not enough balance");
                            case "negative transfer":
                                return ResponseEntity.badRequest().body("You can't create negative transfers");
                            default:
                                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error");
                        }
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("IBAN is null for the account");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
            }
        } catch (Exception e) {
            System.out.println("Error en createTransfer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    private String formatIBAN(String iban){
        if (iban == null) {
            return null;
        }
        iban = iban.replaceAll("\\s+", "");
        StringBuilder formattedIBAN = new StringBuilder();
        for (int i = 0; i < iban.length(); i += 4) {
            if (i > 0) {
                formattedIBAN.append(" ");
            }
            int endIndex = Math.min(i + 4, iban.length());
            formattedIBAN.append(iban.substring(i, endIndex));
        }
        return formattedIBAN.toString();
    }
}
