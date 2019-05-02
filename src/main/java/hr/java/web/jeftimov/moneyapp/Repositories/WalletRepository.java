package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;

import java.util.List;

public interface WalletRepository {
    Wallet findUsersWallet(String userName);
    List<Wallet> findAll();
    Wallet findOne(Long id);
    Wallet save(Wallet wallet);
    Wallet update(Wallet wallet);
    void delete(Long walletId);
}
