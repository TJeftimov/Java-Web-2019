package hr.java.web.jeftimov.moneyapp.Controllers;

import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import hr.java.web.jeftimov.moneyapp.Repositories.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/wallet", produces="application/json")
@CrossOrigin
public class WalletRestController {

    private final WalletRepository walletRepository;

    public WalletRestController(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public Iterable<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> findOne(@PathVariable Long id) {
        Wallet wallet = walletRepository.findById(id).get();

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
        Wallet wlt = walletRepository.findById(id).get();
        wallet.setCreatedate(wlt.getCreatedate());
        wallet.setExpenseList(wlt.getExpenseList());
        wallet.setId(id);
        return walletRepository.save(wallet);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        walletRepository.delete(walletRepository.findById(id).get());
    }
}
