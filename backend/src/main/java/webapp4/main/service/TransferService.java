package webapp4.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp4.main.csv_editor.CSVReader;
import webapp4.main.model.Account;
import webapp4.main.model.Transfer;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.transferDataUtils.ProcessedTransfer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;
    public List<ProcessedTransfer> getProcessedTransfers(String clientIBAN){
        List<Transfer> transferList = transferRepository.findBySenderOrReceiverContaining(clientIBAN);
        ArrayList<ProcessedTransfer> processedTransferList = new ArrayList<>();
        for (Transfer transfer : transferList) {
            ProcessedTransfer processedTransfer = new ProcessedTransfer(transfer, clientIBAN);
            processedTransferList.add(processedTransfer);
        }
        return processedTransferList;
    }
    public Object addTransaction(String username, String receiverIBAN, int amount){
        Optional<Account> accountOptional = accountRepository.findByNIP(username);
        if (accountOptional.isPresent()){
            String accountIBAN = accountOptional.get().getIBAN();
            // Adding to the DB the transaction
            Transfer transfer = new Transfer();
            transfer.setSenderIBAN(accountIBAN);
            transfer.setReceiverIBAN(receiverIBAN);
            transfer.setAmount(amount);
            transfer.setTransferType("transfer");
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");
            String formattedDateTime = currentDateTime.format(formatter);
            transfer.setDate(formattedDateTime);
            transferRepository.save(transfer);
            return transfer;
        }
        return "user not exists";
    }
    public void loadAllTransfers(String pathToCSV){
        CSVReader transferCsvReader = new CSVReader(pathToCSV);
        List<List<String>> transRecords = transferCsvReader.readLines();
        for (int i = 1; i < transRecords.size(); i++) {
            Transfer transfer = new Transfer();
            transfer.setSenderIBAN(transRecords.get(i).get(1));
            transfer.setReceiverIBAN(transRecords.get(i).get(2));
            transfer.setAmount(Integer.parseInt(transRecords.get(i).get(3)));
            transfer.setDate(transRecords.get(i).get(4));
            transfer.setTransferType(transRecords.get(i).get(5));
            transferRepository.save(transfer);
        }
    }
}
