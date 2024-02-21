package webapp4.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webapp4.main.model.Transfer;


public interface TransferRepository extends JpaRepository<Transfer, Long>{
}
