package webapp4.main.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webapp4.main.csv_editor.CSVReader;
import webapp4.main.model.Account;
import webapp4.main.model.Loan;
import webapp4.main.repository.AccountRepository;
import webapp4.main.repository.LoanRepository;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {
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
            loanRepository.save(loan);
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
