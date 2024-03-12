package webapp4.main.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp4.main.model.Account;
import webapp4.main.model.UserData;
import webapp4.main.repository.UserDataRepository;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
public class UserDataService {
    private Random random = new Random();
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private AccountService accountService;
    public boolean registerUser(String inputNIP, String firstName, String lastName, String password){
        Optional<Account> accountOptional = accountService.getUserByNIP(inputNIP);
        if (accountOptional.isPresent()){
            return false;
        } else {
            Account account = new Account();
            account.setNIP(inputNIP);
            String iban = generateIBAN();
            account.setIBAN(iban);
            account.setName(firstName);
            account.setSurname(lastName);
            accountService.createAccount(account);
            UserData userData = new UserData();
            userData.setUsername(inputNIP);
            PasswordEncoder passwordEncoder = passwordEncoder();
            userData.setPassword(passwordEncoder.encode(password));
            userData.setRole("USER");
            userDataRepository.save(userData);
            return true;
        }
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    private @NotNull String generateIBAN() {
        String countryCode = "ES";
        int ibanLength = 24;
        StringBuilder ibanBuilder = new StringBuilder(countryCode);
        for (int i = 0; i < ibanLength - countryCode.length(); i++) {
            if ((ibanLength + 2 - i) % 4 == 0){
                ibanBuilder.append(" ");
            }
            ibanBuilder.append(random.nextInt(10));
        }
        return ibanBuilder.toString();
    }
}
