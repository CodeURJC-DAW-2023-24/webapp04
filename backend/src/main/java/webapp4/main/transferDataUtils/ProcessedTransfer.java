package webapp4.main.transferDataUtils;

import webapp4.main.model.Transfer;

public class ProcessedTransfer {
    private final Long transfer_id;
    private final String clientIBAN;
    private final String senderIBAN;
    private final String receiverIBAN;
    private final int amount;
    private final String date;
    private final String transferType;

    public ProcessedTransfer(Transfer transfer, String clientIBAN){
        this.transfer_id = transfer.getTransfer_id();
        this.clientIBAN = clientIBAN;
        this.senderIBAN = transfer.getSenderIBAN();
        this.receiverIBAN = transfer.getReceiverIBAN();
        this.amount = transfer.getAmount();
        this.date = transfer.getDate();
        this.transferType = transfer.getTransferType();
    }

    public Long getTransfer_id() {
        return transfer_id;
    }

    public String getClientIBAN() {
        return clientIBAN;
    }

    public String getSenderIBAN() {
        if (clientIBAN.equals(senderIBAN)){
            return "You";
        }
        return senderIBAN.substring(senderIBAN.length() - 4);
    }

    public String getReceiverIBAN() {
        if (clientIBAN.equals(receiverIBAN)){
            return "You";
        }
        return receiverIBAN.substring(receiverIBAN.length() - 4);
    }

    public int getAmount() {
        if (getReceiverIBAN().equals("You")){
            return amount;
        }
        return -amount;
    }

    public String getDate() {
        return date;
    }

    public String getTransferType() {
        return transferType;
    }
}
