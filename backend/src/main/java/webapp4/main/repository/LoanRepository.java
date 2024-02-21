package webapp4.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webapp4.main.model.Loan;


public interface LoanRepository extends JpaRepository<Loan, Long>{

}
