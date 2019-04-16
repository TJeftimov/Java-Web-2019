package hr.java.web.jeftimov.moneyapp.Entities;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class Wallet {

    private Long id;

    private String walletName;

    private ArrayList<Expense> expenseList;

    private WalletType walletType;

    private LocalDateTime createDate;

    public enum WalletType {
        LEATHER,
        LINEN
    }

    public Wallet() {
        expenseList = new ArrayList<>();
    }

    public Double sumOfExpenses() {

        Double sumOfExpenses = 0.0;
        for(Expense n : expenseList) {
            sumOfExpenses += n.getAmount();
        }

        return sumOfExpenses;
    }
}
