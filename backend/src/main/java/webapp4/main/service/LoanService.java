package webapp4.main.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {
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
}
