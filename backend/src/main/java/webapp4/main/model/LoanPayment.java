package webapp4.main.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LoanPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int period;

    private float principal;

    private float interest;

    private float remainingAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id") // Esta es la columna que guardará el ID del Loan
    @JsonIgnore
    private Loan loan;


    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public float getPrincipal() {
        return principal;
    }

    public void setPrincipal(float principal) {
        this.principal = principal;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }


    public float getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(float remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}