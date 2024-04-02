package webapp4.main.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DBInitializer {
    @Autowired
    private LoanService loanService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private UserDataService userDataService;
    @PostConstruct
    public void init() {
        loanService.loadAllLoans("static/data/loans_data.csv");
        accountService.loadAllAccounts("static/data/client_data.csv");
        transferService.loadAllTransfers("static/data/transfers_data.csv");
        userDataService.loadAllCLientData("static/data/client_credentials.csv");
    }
}
