package webapp4.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webapp4.main.model.Account;


public interface AccountRepository extends JpaRepository<Account, Long>{

}

