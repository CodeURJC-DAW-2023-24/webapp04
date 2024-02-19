package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{

}
