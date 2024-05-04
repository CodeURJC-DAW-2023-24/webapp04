export interface Transfer {
    transfer_id?: number;
    senderIBAN: string;
    receiverIBAN: string;
    amount: number;
    date: string;
    transferType: string;
  }
  