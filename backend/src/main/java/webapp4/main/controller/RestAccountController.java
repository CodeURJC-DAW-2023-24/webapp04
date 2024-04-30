package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
import webapp4.main.model.CreateAccountRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import webapp4.main.model.Account;
import webapp4.main.model.AccountDTO;
import webapp4.main.model.ImagelessAccount;
import webapp4.main.repository.AccountRepository;
import webapp4.main.service.AccountService;
import webapp4.main.service.UserDataService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Collection;

import javax.sql.rowset.serial.SerialException;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@CrossOrigin(origins = "*")
@RestController
public class RestAccountController {
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

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
    public ResponseEntity<?> createAccount(Model model, @RequestBody CreateAccountRequest accountJSON) {
        String inputUser = accountJSON.getInputUser();
        String firstName = accountJSON.getName();
        String lastName = accountJSON.getSurname();
        String inputPassword = accountJSON.getPassword();
        String confirmPassword = accountJSON.getConfirmPassword();
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
    @Operation (summary = "Get an account by id")
    @ApiResponse(
            responseCode = "200",
            description = "Found the Account",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDTO.class))
    )
    @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/accounts/{id}")
    public ResponseEntity<ImagelessAccount> getAccount(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (id.equals(username)) {
            Optional<Account> accountOptional = accountRepository.findByNIP(id);
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                ImagelessAccount imagelessAccount = accountService.accountWithoutImage(account);
                return ResponseEntity.ok().body(imagelessAccount);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
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
    @Operation (summary = "Get all accounts")
    @ApiResponse(
            responseCode = "200",
            description = "Found the form",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Account.class))
    )
    @ApiResponse(responseCode = "404", description = "Account Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/accounts")
    public ResponseEntity<Collection<ImagelessAccount>> getAllAccounts(){
        Collection<ImagelessAccount> imagelessAccounts = new ArrayList<>();
        Collection<Account> allAccounts = accountRepository.findAll();
        allAccounts.forEach(account -> imagelessAccounts.add(accountService.accountWithoutImage(account)));
        return ResponseEntity.ok(imagelessAccounts);
    }
    @Operation(summary = "Get paginated accounts")
    @ApiResponse(
            responseCode = "200",
            description = "Found the accounts",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ImagelessAccount.class))
    )
    @ApiResponse(responseCode = "404", description = "Account Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/accounts/paged")
    public ResponseEntity<Page<ImagelessAccount>> getAllAccountsPaged(Pageable page) {
        Page<Account> accountPage = accountRepository.findAll(page);
        Page<ImagelessAccount> imagelessAccountPage = accountPage.map(account -> accountService.accountWithoutImage(account));
        return ResponseEntity.ok(imagelessAccountPage);
    }
}
