package webapp4.main.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webapp4.main.csv_editor.CSVReader;
import webapp4.main.model.Account;
import webapp4.main.model.UserData;
import webapp4.main.repository.UserDataRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class UserDataService {
    private Random random = new Random();
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private AccountService accountService;
    
    public Object registerUser(String inputNIP, String firstName, String lastName, String password, String confirmPassword){
        Optional<Account> accountOptional = accountService.getUserByNIP(inputNIP);
        if (accountOptional.isPresent()){
            return "Account already exists";
        } else {
            if (password.equals(confirmPassword)) {
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
                return account;
            } else {
                return "Error with confirm password";
            }
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
    public void loadAllCLientData(String pathToCSV){
        CSVReader userDataCsvReader = new CSVReader(pathToCSV);
        List<List<String>> userDataRecords = userDataCsvReader.readLines();
        PasswordEncoder passwordEncoder = passwordEncoder();
        for (int i = 1; i < userDataRecords.size(); i++) {
            UserData userData = new UserData();
            userData.setUsername(userDataRecords.get(i).get(1));
            userData.setPassword(passwordEncoder.encode(userDataRecords.get(i).get(2)));
            if(Objects.equals(userData.getUsername(), "00000000A")){
                userData.setRole("ADMIN");
            }else {
                userData.setRole("USER");
            }
            userDataRepository.save(userData);
        }
    }
}
