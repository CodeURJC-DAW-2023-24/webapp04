package webapp4.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp4.main.csv_editor.CSVReader;
import webapp4.main.model.Account;
import webapp4.main.model.Loan;
import webapp4.main.model.LoanPayment;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.LoanRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoanRepository loanRepository;

    public static final String[] loanTypes = {
            "Housing", "Education", "Medical", "Other"
    };

    private static final float interestRate = 7.5f;

    public List<Map<String, Float>> getLoanInfo(int amount, int installments, int startIndex, int chunkSize){
        List<Map<String, Float>> loanPayments = new ArrayList<>();
        float monthlyInterestRate = interestRate / 100 / 12;
        float remainingAmount = amount;
        for (int i = startIndex; i < startIndex + chunkSize; i++) {
            if (i >= installments) {
                break;
            }
            float interestPayment = remainingAmount * monthlyInterestRate;
            float principalPayment = (float) amount / installments;
            Map<String, Float> payment = new HashMap<>();
            payment.put("period", (float) (i + 1));
            payment.put("principal", principalPayment);
            payment.put("interest", interestPayment);
            payment.put("remainingAmount", remainingAmount);
            loanPayments.add(payment);
            remainingAmount -= principalPayment;
        }
        return loanPayments;
    }

    public void loadAllLoans(String pathToCSV){
        CSVReader loanCsvReader = new CSVReader(pathToCSV);
        List<List<String>> records = loanCsvReader.readLines();
        for (int i = 1; i < records.size(); i++) {
            Loan loan = new Loan();
            loan.setClientID(records.get(i).get(0));
            loan.setAmount(Integer.parseInt(records.get(i).get(1)));
            loan.setInterest_rate(Float.parseFloat(records.get(i).get(2)));
            loan.setPeriods(Integer.parseInt(records.get(i).get(3)));
            loan.setDate(records.get(i).get(4));

            // Aquí creamos los pagos de préstamos asociados con el préstamo y los guardamos
            List<LoanPayment> loanPayments = calculateLoanPayments(loan.getAmount(), loan.getPeriods());
            loan.setLoanPayments(loanPayments);
            for (LoanPayment loanPayment : loanPayments) {
                loanPayment.setLoan(loan);
            }
            loanRepository.save(loan);
        }
    }

    public Object addLoan(String username, int amount, int periods){
        Optional<Account> accountOptional = accountRepository.findByNIP(username);
        if (accountOptional.isPresent()){
            String accountNIP = accountOptional.get().getNIP();
            if (amount < 0){
                return "negative amount";
            }
            if (periods < 1){
                return "negative periods";
            }
            // Adding to the DB the loan
            Loan loan = new Loan();
            loan.setClientID(accountNIP);
            loan.setAmount(amount);
            loan.setInterest_rate(interestRate);
            loan.setPeriods(periods);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");
            String formattedDateTime = currentDateTime.format(formatter);
            loan.setDate(formattedDateTime);

            // Aquí creamos los pagos de préstamos asociados con el préstamo y los guardamos
            List<LoanPayment> loanPayments = calculateLoanPayments(amount, periods);
            for (LoanPayment loanPayment : loanPayments) {
                loanPayment.setLoan(loan);
            }
            loan.setLoanPayments(loanPayments);

            loanRepository.save(loan);
            return loan;
        }
        return "user not exists";
    }

    private List<LoanPayment> calculateLoanPayments(int amount, int installments) {
        List<LoanPayment> loanPayments = new ArrayList<>();
        float monthlyInterestRate = interestRate / 100 / 12;
        float remainingAmount = amount;
        for (int i = 0; i < installments; i++) {
            if (i >= installments) {
                break;
            }
            float interestPayment = remainingAmount * monthlyInterestRate;
            float principalPayment = (float) amount / installments;
            LoanPayment payment = new LoanPayment();
            payment.setPeriod(i + 1);
            payment.setPrincipal(principalPayment);
            payment.setInterest(interestPayment);
            payment.setRemainingAmount(remainingAmount);
            loanPayments.add(payment);
            remainingAmount -= principalPayment;
        }
        return loanPayments;
    }
}