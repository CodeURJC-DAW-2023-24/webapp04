package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.service.TransferService;
import webapp4.main.service.UserDataService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
public class RestTransferController {


    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferRepository transferRepository;


@Operation  (summary = "Create transfer")
    @ApiResponse(
            responseCode = "200",
            description = "Transfer Created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class))
    )
    @ApiResponse(responseCode = "404", description = "User not Exist", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
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
            if (errorMessage.equals("User not Exists")){
                return  ResponseEntity.badRequest().body("User not exists");
            }
            else
            return ResponseEntity.ok("Transfer Created");
        }
        }
    
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


}
