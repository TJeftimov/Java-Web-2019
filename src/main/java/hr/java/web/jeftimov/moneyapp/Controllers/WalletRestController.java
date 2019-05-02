package hr.java.web.jeftimov.moneyapp.Controllers;

import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import hr.java.web.jeftimov.moneyapp.Repositories.HibernateWalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/wallet", produces="application/json")
@CrossOrigin
public class WalletRestController {

    private final HibernateWalletRepository walletRepository;

    public WalletRestController(HibernateWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public Iterable<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> findOne(@PathVariable Long id) {
        Wallet wallet = walletRepository.findOne(id);

        if(wallet != null) {
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes="application/json")
    public Wallet save(@RequestBody Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @PutMapping("/{id}")
    public Wallet update(@PathVariable Long id, @RequestBody Wallet wallet) {
        Wallet wlt = walletRepository.findOne(id);
        wallet.setCreateDate(wlt.getCreateDate());
        wallet.setExpenseList(wlt.getExpenseList());
        wallet.setId(id);
        return walletRepository.update(wallet);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        walletRepository.delete(id);
    }
}
