package service;

import jakarta.annotation.PostConstruct;
import model.Account;
import model.Loan;
import model.Transfer;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import repository.AccountRepository;
import repository.LoanRepository;
import repository.TransferRepository;
import repository.UserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Service
public class DBInitializer {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        /* UPLOADING LOAN DATA */
        String loanData = "../resources/static/data/loans_data.csv";
        try (Reader reader = new FileReader(loanData);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                Loan auxLoan = new Loan();

                auxLoan.setClientID(csvRecord.get(0));
                auxLoan.setAmount(Integer.parseInt(csvRecord.get(1)));
                auxLoan.setInterest_rate(Float.parseFloat(csvRecord.get(2)));
                auxLoan.setPeriods(Integer.parseInt(csvRecord.get(3)));
                auxLoan.setDate(csvRecord.get(4));

                loanRepository.save(auxLoan);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* UPLOADING ACCOUNT DATA */
        String accountData = "../resources/static/data/client_data.csv";
        try (Reader reader = new FileReader(accountData);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                Account auxAccount = new Account();

                auxAccount.setNIP(csvRecord.get(0));
                auxAccount.setIBAN(csvRecord.get(1));
                auxAccount.setName(csvRecord.get(2));
                auxAccount.setSurname(csvRecord.get(3));
                setClientImage(auxAccount, "../resources/static/Client_profile_pics/" + auxAccount.getIBAN() + ".jpeg");

                accountRepository.save(auxAccount);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* UPLOADING TRANSFERS */
        String transferData = "../resources/static/data/transfers_data.csv";
        try (Reader reader = new FileReader(transferData);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : csvParser) {
                Transfer auxTransfer = new Transfer();

                auxTransfer.setSenderIBAN(csvRecord.get(0));
                auxTransfer.setReceiverIBAN(csvRecord.get(1));
                auxTransfer.setAmount(Integer.parseInt(csvRecord.get(2)));
                auxTransfer.setDate(csvRecord.get(3));
                auxTransfer.setTransferType(csvRecord.get(4));

                transferRepository.save(auxTransfer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setClientImage(Account bankClient, String classpathResource) throws IOException {
        Resource image = new ClassPathResource(classpathResource);
        bankClient.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));
    }
}
