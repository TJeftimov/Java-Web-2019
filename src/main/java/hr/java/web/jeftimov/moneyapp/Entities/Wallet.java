package hr.java.web.jeftimov.moneyapp.Entities;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="wallets")
public class Wallet implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String walletName;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    private List<Expense> expenseList;

    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    private LocalDateTime createDate;

    private String userName;

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
