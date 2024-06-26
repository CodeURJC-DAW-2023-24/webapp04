package webapp4.main.model;

public class TransferDTO {

    private String receiverIBAN;
    private int amount;

    public TransferDTO(){}

    public TransferDTO(String receiverIBAN, int amount){
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
    }

    public String getReceiverIBAN(){
        return receiverIBAN;
    }

    public void setReceiverIBAN(String value){
        receiverIBAN = value;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int value){
        amount = value;
    }
}
