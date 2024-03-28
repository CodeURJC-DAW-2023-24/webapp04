package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import webapp4.main.model.Account;
import webapp4.main.repository.AccountRepository;
import webapp4.main.service.UserDataService;
import java.util.Optional;

import java.net.URI;


import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
public class RestAccountController {


    @Autowired
    private UserDataService userDataService;
    @Autowired
    private AccountRepository accountRepository;


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

}
