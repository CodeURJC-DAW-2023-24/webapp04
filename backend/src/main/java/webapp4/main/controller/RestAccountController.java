package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import webapp4.main.model.Account;
import webapp4.main.repository.AccountRepository;
import webapp4.main.service.AccountService;
import webapp4.main.service.UserDataService;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
public class RestAccountController {


    @Autowired
    private UserDataService userDataService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;


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

    /*@GetMapping("/api/accounts/{accountId}/image")                //this code is for get the image, the other one is to  download, u must decide which one u want to keep
    public ResponseEntity<byte[]> getImage(@PathVariable String accountId) {
        Optional<Account> accountOptional = accountRepository.findByNIP(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Blob imageBlob = account.getImageFile();

            try     {
                byte[] imageBytes = accountService.getImageBytes(imageBlob);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE));

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } catch (SQLException | IOException e) {
                // Manejar errores de conversión
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @PostMapping("/api/accounts/{accountId}/image")
    public ResponseEntity<?> uploadImage(@PathVariable String accountId, @RequestParam MultipartFile imageFile) throws IOException, SerialException, SQLException{
        Optional<Account> accountOptional = accountRepository.findByNIP(accountId);
        if (accountOptional.isPresent()){
            URI location = fromCurrentRequest().build().toUri();
            Account account = accountOptional.get();
            byte[] bytes = imageFile.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            account.setImageFile(blob);
            accountRepository.save(account);
            return ResponseEntity.created(location).build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/accounts/{accountId}/image")
    public ResponseEntity<Object> deleteImage(@PathVariable String accountId) throws IOException {
        Optional<Account> accountOptional = accountRepository.findByNIP(accountId);
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();
            account.setImageFile(null);
            accountRepository.save(account);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/accounts/{accountId}/image")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String accountId) {
        Optional<Account> accountOptional = accountRepository.findByNIP(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Blob imageBlob = account.getImageFile();

            try {
                byte[] imageBytes = accountService.getImageBytes(imageBlob);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            } catch (SQLException | IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
