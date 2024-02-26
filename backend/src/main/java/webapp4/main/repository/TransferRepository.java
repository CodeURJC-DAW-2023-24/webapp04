package webapp4.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webapp4.main.model.Transfer;

import java.util.List;


public interface TransferRepository extends JpaRepository<Transfer, Long>{
    @Query("SELECT u FROM Transfer u WHERE u.senderIBAN LIKE %?1% OR u.receiverIBAN LIKE %?1%")
    List<Transfer> findBySenderOrReceiverContaining(String searchTerm);
}
