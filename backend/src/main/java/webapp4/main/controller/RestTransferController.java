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

    @Operation(summary = "Get transfers based on user role")
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
    
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            Page<Transfer> allTransfers = transferRepository.findAll(paging);
            return ResponseEntity.ok(allTransfers);
        } else {
            Optional<Account> accountOptional = accountRepository.findByNIP(authentication.getName());
            if (accountOptional.isPresent()) {
                String accountIBAN = accountOptional.get().getIBAN();
                Page<Transfer> userTransfers = transferRepository.findBySenderOrReceiverContainingPaged(accountIBAN, paging);
                return ResponseEntity.ok(userTransfers);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

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
                return ResponseEntity.badRequest().body("There is not enough balance");
            }
            if (errorMessage.equals("negative transfer")) {
                return ResponseEntity.badRequest().body("You can't create negative transfers");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
