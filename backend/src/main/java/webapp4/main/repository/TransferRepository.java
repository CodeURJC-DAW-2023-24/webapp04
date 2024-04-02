package webapp4.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webapp4.main.model.Transfer;

import java.util.List;
import java.util.Optional;


public interface TransferRepository extends JpaRepository<Transfer, Long>{
    @Query("SELECT u FROM Transfer u WHERE u.senderIBAN LIKE %?1% OR u.receiverIBAN LIKE %?1% ORDER BY u.date DESC")
    List<Transfer> findBySenderOrReceiverContaining(String searchTerm);

    @Override
    Optional<Transfer> findById(Long id);
}
