package webapp4.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webapp4.main.model.Account;
import webapp4.main.repository.AccountRepository;

import java.sql.Blob;
import java.sql.SQLException;
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
}
