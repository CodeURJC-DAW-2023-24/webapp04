package webapp4.main.service;

import javax.annotation.PostConstruct;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import webapp4.main.csv_editor.CSVReader;

import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.LoanRepository;
import webapp4.main.repository.TransferRepository;
import webapp4.main.repository.UserDataRepository;

import webapp4.main.model.Account;
import webapp4.main.model.Loan;
import webapp4.main.model.Transfer;
import webapp4.main.model.UserData;

import javax.sql.rowset.serial.SerialBlob;

@Service
public class DBInitializer {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private UserDataService userDataService;
    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        loanService.loadAllLoans("backend/src/main/resources/static/data/loans_data.csv");
        accountService.loadAllAccounts("backend/src/main/resources/static/data/client_data.csv");
        transferService.loadAllTransfers("backend/src/main/resources/static/data/transfers_data.csv");
        userDataService.loadAllCLientData("backend/src/main/resources/static/data/client_credentials.csv");
    }
}
