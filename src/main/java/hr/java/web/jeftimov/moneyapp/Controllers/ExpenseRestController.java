package hr.java.web.jeftimov.moneyapp.Controllers;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import hr.java.web.jeftimov.moneyapp.Repositories.HibernateExpenseRepository;
import hr.java.web.jeftimov.moneyapp.Repositories.HibernateWalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/expense", produces="application/json")
@CrossOrigin
public class ExpenseRestController {

    private final HibernateExpenseRepository expenseRepository;
    private final HibernateWalletRepository walletRepository;

    public ExpenseRestController(HibernateExpenseRepository expenseRepository, HibernateWalletRepository walletRepository) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public Iterable<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findOne(@PathVariable Long id) {
        Expense expense = expenseRepository.findOne(id);

        if(expense != null) {
            return new ResponseEntity<>(expense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes="application/json")
    public Expense save(@RequestBody Expense expense, @RequestParam Long walletId) {
        Wallet wallet = walletRepository.findOne(walletId);
        return expenseRepository.save(expense, wallet);
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense expense) {
        Expense exp = expenseRepository.findOne(id);
        expense.setCreateDate(exp.getCreateDate());
        expense.setWallet(exp.getWallet());
        expense.setId(id);
        return expenseRepository.update(expense);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        expenseRepository.delete(id);
    }
}
