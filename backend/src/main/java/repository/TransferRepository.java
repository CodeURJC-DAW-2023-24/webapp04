package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long>{
}
