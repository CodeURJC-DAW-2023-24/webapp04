package webapp4.main.service;

import jakarta.annotation.PostConstruct;

import org.hibernate.engine.jdbc.BlobProxy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import webapp4.main.csv_editor.CSVReader;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.LoanRepository;
import webapp4.main.repository.TransferRepository;

import webapp4.main.model.Account;
import webapp4.main.model.Loan;
import webapp4.main.model.Transfer;

import javax.sql.rowset.serial.SerialBlob;

@Service
public class DBInitializer {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;
    @PostConstruct
    public void init() throws IOException, URISyntaxException {

        System.out.println("--------------");
        System.out.println("1st CHECKPOINT");
        System.out.println("--------------");


        /* UPLOADING LOAN DATA */
        String loanData = "backend/src/main/resources/static/data/loans_data.csv";
        CSVReader loanCsvReader = new CSVReader(loanData);
        List<List<String>> records = loanCsvReader.readLines();
        for (int i = 1; i < records.size(); i++) {
            Loan loan = new Loan();
            loan.setClientID(records.get(i).get(0));
            loan.setAmount(Integer.parseInt(records.get(i).get(1)));
            loan.setInterest_rate(Float.parseFloat(records.get(i).get(2)));
            loan.setPeriods(Integer.parseInt(records.get(i).get(3)));
            loan.setDate(records.get(i).get(4));
            loanRepository.save(loan);
        }

        System.out.println("--------------");
        System.out.println("2nd CHECKPOINT");
        System.out.println("--------------");

        /* UPLOADING ACCOUNT DATA */
        String accountData = "backend/src/main/resources/static/data/client_data.csv";
        CSVReader accountCsvReader = new CSVReader(accountData);
        List<List<String>> accRecords = accountCsvReader.readLines();
        for (int i = 1; i < accRecords.size(); i++) {
            Account account = new Account();
            account.setNIP(accRecords.get(i).get(0));
            System.out.println(account.getNIP());
            account.setIBAN(accRecords.get(i).get(1));
            System.out.println(account.getIBAN());
            account.setName(accRecords.get(i).get(2));
            System.out.println(account.getName());
            account.setSurname(accRecords.get(i).get(3));
            System.out.println(account.getSurname());
            setClientImage(account, "backend/src/main/resources/static/Client_profile_pics/" + account.getNIP() + ".jpeg");
            accountRepository.save(account);
        }

        System.out.println("--------------");
        System.out.println("3rd CHECKPOINT");
        System.out.println("--------------");

        /* UPLOADING TRANSFERS DATA */
        String transferData = "backend/src/main/resources/static/data/transfers_data.csv";
        CSVReader transferCsvReader = new CSVReader(transferData);
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
    private void setClientImage(@NotNull Account bankClient, String imagePath){
        try {
            FileInputStream fis = new FileInputStream(imagePath);
            byte[] imageBin = fis.readAllBytes();
            SerialBlob serialBlob = new SerialBlob(imageBin);
            bankClient.setImageFile(serialBlob);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
