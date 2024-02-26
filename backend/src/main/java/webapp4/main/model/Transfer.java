package webapp4.main.model;

import jakarta.persistence.*;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transfer_id = null;
    @Column(columnDefinition = "TEXT")
    private String senderIBAN;
    @Column(columnDefinition = "TEXT")
    private String receiverIBAN;
    private int amount;
    @Column(columnDefinition = "TEXT")
    private String date;
    @Column(columnDefinition = "TEXT")
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
