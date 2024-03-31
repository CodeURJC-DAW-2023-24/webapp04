package webapp4.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webapp4.main.model.Account;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByNIP(String NIP);
    Optional<Account> findByIBAN(String IBAN);
}

