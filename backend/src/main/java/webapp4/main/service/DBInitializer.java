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
        loanService.loadAllLoans("backend/src/main/resources/static/data/loans_data.csv");
        accountService.loadAllAccounts("backend/src/main/resources/static/data/client_data.csv");
        transferService.loadAllTransfers("backend/src/main/resources/static/data/transfers_data.csv");
        userDataService.loadAllCLientData("backend/src/main/resources/static/data/client_credentials.csv");
    }
}
