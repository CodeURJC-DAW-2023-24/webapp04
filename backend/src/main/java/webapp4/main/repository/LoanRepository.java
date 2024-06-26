package webapp4.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import webapp4.main.model.Loan;


public interface LoanRepository extends JpaRepository<Loan, Long>{
    @Query("SELECT u FROM Loan u WHERE u.client_id LIKE %:clientId% ORDER BY u.loan_id DESC")
    List<Loan> findByClientId(@Param("clientId") String clientId);

    @Query("SELECT u FROM Loan u WHERE u.client_id LIKE %:clientId% ORDER BY u.loan_id DESC")
    Page<Loan> findByClientIdPaged(@Param("clientId") String clientId, Pageable page);



    @Override
    Optional<Loan> findById(Long loan_id);
}
