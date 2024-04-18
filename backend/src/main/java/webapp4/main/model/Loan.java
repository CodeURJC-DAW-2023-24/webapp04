package webapp4.main.model;

import javax.persistence.*;
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loan_id = null;
    @Column(columnDefinition = "TEXT")
    private String client_id;
    private int amount;
    private float interest_rate;
    private int periods;
    @Column(columnDefinition = "TEXT")
    private String date;

    public Loan() {
        super();
    }


    public Long getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(Long loan_id) {
        this.loan_id = loan_id;
    }

    public String getClientID() {
        return client_id;
    }

    public void setClientID(String client_id) {
        this.client_id = client_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(float interest_rate) {
        this.interest_rate = interest_rate;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
