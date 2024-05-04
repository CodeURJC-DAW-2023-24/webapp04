package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.service.AccountService;
import webapp4.main.service.UserDataService;

import java.util.*;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import webapp4.main.transferDataUtils.ProcessedTransfer;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
public class RestAccountController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferRepository transferRepository;

    @Operation (summary = "Create New Account")
    @ApiResponse(
            responseCode = "200",
            description = "Account Created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
    )
    @ApiResponse(responseCode = "404", description = "Account not Found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @PostMapping("/api/accounts")
    public ResponseEntity<?> createAccount(Model model, @RequestParam String inputUser, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String inputPassword, @RequestParam String confirmPassword) {
        Object registerUser = userDataService.registerUser(inputUser, firstName, lastName, inputPassword, confirmPassword);
        if (registerUser instanceof Account){
            Account account = (Account) registerUser;
            URI location = fromCurrentRequest().path("/{id}")
                    .buildAndExpand(account.getNIP()).toUri();
            return ResponseEntity.created(location).body(account);
        } else {
            String errorMessage = (String) registerUser;
            if (errorMessage.equals("Account already exists")){
                return  ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists");
            } else{
                return ResponseEntity.badRequest().body("Error with confirm Password");
            }
        }
    }
    @Operation (summary = "Get the registered account data")
    @ApiResponse(
            responseCode = "200",
            description = "Found the Account",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
    )
    @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/accounts/personal")
    public ResponseEntity<Map<String, Object>> getRegAccount(Authentication authentication){
        Optional<Account> accountOptional = accountRepository.findByNIP(authentication.getName());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("nip", account.getNIP());
            responseData.put("name", account.getName());
            responseData.put("surname", account.getSurname());
            responseData.put("iban", account.getIBAN());
            List<Transfer> transferList = transferRepository.findBySenderOrReceiverContaining(account.getIBAN());
            ArrayList<ProcessedTransfer> processedTransferList = new ArrayList<>();
            int balance = 0;
            for (Transfer transfer : transferList) {
                ProcessedTransfer processedTransfer = new ProcessedTransfer(transfer, account.getIBAN());
                processedTransferList.add(processedTransfer);
                balance += processedTransfer.getAmount();
            }
            responseData.put("balance", balance);
            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     @Operation (summary = "Get an account by id")
    @ApiResponse(
            responseCode = "200",
            description = "Found the Account",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
    )
    @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/accounts/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable String id){
        Optional<Account> accountOptional = accountRepository.findByNIP(id);
        Account account = accountOptional.get();
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/accounts")
    public ResponseEntity<Collection<Account>> getAllAccounts(){
        Collection<Account> allAccounts = accountRepository.findAll();
        return ResponseEntity.ok(allAccounts);
    }

@Operation
     @ApiResponse(
            responseCode = "200",
            description = "Get Image by ID",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
    )
    @ApiResponse(responseCode = "404", description = "Form not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/accounts/{accountId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String accountId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> accountOptional = accountRepository.findByNIP(username);
        if (accountOptional.isPresent()) {
            if(accountId.equals(username)){
                Account account = accountOptional.get();
                Blob imageBlob = account.getImageFile();
                if (imageBlob == null){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                try {
                    byte[] imageBytes = accountService.getImageBytes(imageBlob);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE));
    
                    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
                } catch (SQLException | IOException e) {
                    // Manejar errores de conversión
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }else{
                return ResponseEntity.badRequest().build();
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     @Operation
    (summary = "Upload image by ID")
    @ApiResponse(
            responseCode = "200",
            description = "Found the form",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
    )
    @ApiResponse(responseCode = "404", description = "Form not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @PostMapping("/api/accounts/{accountId}/image")
    public ResponseEntity<?> uploadImage(@PathVariable String accountId, @RequestParam MultipartFile imageFile) throws IOException, SerialException, SQLException{
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> accountOptional = accountRepository.findByNIP(username);
        if (accountOptional.isPresent()){
            if(accountId.equals(username)){
                URI location = fromCurrentRequest().build().toUri();
                Account account = accountOptional.get();
                byte[] bytes = imageFile.getBytes();
                Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
                account.setImageFile(blob);
                accountRepository.save(account);
                return ResponseEntity.created(location).build();
            }else{
                return ResponseEntity.badRequest().build();
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     @Operation
    (summary = "Delete an image by id")
    @ApiResponse(
            responseCode = "200",
            description = "Image Deleted",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
    )
    @ApiResponse(responseCode = "404", description = "Image not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @DeleteMapping("/api/accounts/{accountId}/image")
    public ResponseEntity<Object> deleteImage(@PathVariable String accountId) throws IOException {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<Account> accountOptional = accountRepository.findByNIP(username);
        if (accountOptional.isPresent()){
            if(accountId.equals(username)){
                Account account = accountOptional.get();
                account.setImageFile(null);
                accountRepository.save(account);
                return ResponseEntity.noContent().build();
            }else{
                return ResponseEntity.badRequest().build();    
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
