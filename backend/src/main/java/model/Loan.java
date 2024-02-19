package model;

import jakarta.persistence.*;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loan_id = null;
    private String clientID;
    private int amount;
    private float interest_rate;
    private int periods;
    private String date;

    public Loan() {
        super();
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
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
