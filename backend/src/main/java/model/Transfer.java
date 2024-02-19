package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transfer_id = null;
    private String senderIBAN;
    private String receiverIBAN;
    private int amount;
    private String date;
    private String transferType;

    public Transfer(){
        super();
    }

    public String getSenderIBAN() {
        return senderIBAN;
    }

    public void setSenderIBAN(String senderIBAN) {
        this.senderIBAN = senderIBAN;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public void setReceiverIBAN(String receiverIBAN) {
        this.receiverIBAN = receiverIBAN;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
}
