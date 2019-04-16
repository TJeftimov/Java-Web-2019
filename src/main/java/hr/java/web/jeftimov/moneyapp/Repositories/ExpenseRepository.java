package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;

import java.util.List;

public interface ExpenseRepository {
    List<Expense> findAll();
    Expense findOne(Long id);
    Expense save(Expense expense, Long walletId);
    List<Expense> getExpensesList(Wallet wallet);
}
