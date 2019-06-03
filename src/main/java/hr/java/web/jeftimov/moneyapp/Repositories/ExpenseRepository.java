package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findByWallet(Wallet wallet);
}
