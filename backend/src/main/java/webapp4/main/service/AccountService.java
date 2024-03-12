package webapp4.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp4.main.model.Account;
import webapp4.main.repository.AccountRepository;

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
}
