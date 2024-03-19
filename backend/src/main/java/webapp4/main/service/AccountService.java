package webapp4.main.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webapp4.main.csv_editor.CSVReader;
import webapp4.main.model.Account;
import webapp4.main.repository.AccountRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public Optional<Account> getUserByNIP(String NIP){
        return accountRepository.findByNIP(NIP);
    }

    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    public byte[] getProfilePicBytes(String clientNIP) {
        Optional<Account> clientData = accountRepository.findByNIP(clientNIP);
        if (clientData.isPresent()) {
            Account account = clientData.get();
            Blob imageBlob = account.getImageFile();
            try {
                return imageBlob.getBytes(1, (int) imageBlob.length());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return new byte[0];
    }

    public void loadAllAccounts(String pathToCSV){
        CSVReader accountCsvReader = new CSVReader(pathToCSV);
        List<List<String>> accRecords = accountCsvReader.readLines();
        for (int i = 1; i < accRecords.size(); i++) {
            Account account = new Account();
            account.setNIP(accRecords.get(i).get(0));
            account.setIBAN(accRecords.get(i).get(1));
            account.setName(accRecords.get(i).get(2));
            account.setSurname(accRecords.get(i).get(3));
            setClientImage(account, "backend/src/main/resources/static/Client_profile_pics/" + account.getNIP() + ".jpeg");
            accountRepository.save(account);
        }
    }
    public void setClientImage(@NotNull Account bankClient, String imagePath){
        try {
            FileInputStream fis = new FileInputStream(imagePath);
            byte[] imageBin = fis.readAllBytes();
            SerialBlob serialBlob = new SerialBlob(imageBin);
            bankClient.setImageFile(serialBlob);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
