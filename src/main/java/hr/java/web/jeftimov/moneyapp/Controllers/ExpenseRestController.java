package hr.java.web.jeftimov.moneyapp.Controllers;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import hr.java.web.jeftimov.moneyapp.Repositories.ExpenseRepository;
import hr.java.web.jeftimov.moneyapp.Repositories.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/expense", produces="application/json")
@CrossOrigin
public class ExpenseRestController {

    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;

    public ExpenseRestController(ExpenseRepository expenseRepository, WalletRepository walletRepository) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public Iterable<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findOne(@PathVariable Long id) {
        Expense expense = expenseRepository.findById(id).get();

        if(expense != null) {
            return new ResponseEntity<>(expense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes="application/json")
    public Expense save(@RequestBody Expense expense, @RequestParam Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).get();
        expense.setWallet(wallet);
        return expenseRepository.save(expense);
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id, @RequestBody Expense expense) {
        Expense exp = expenseRepository.findById(id).get();
        expense.setCreatedate(exp.getCreatedate());
        expense.setWallet(exp.getWallet());
        expense.setId(id);
        return expenseRepository.save(expense);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        expenseRepository.delete(expenseRepository.findById(id).get());
    }
}
