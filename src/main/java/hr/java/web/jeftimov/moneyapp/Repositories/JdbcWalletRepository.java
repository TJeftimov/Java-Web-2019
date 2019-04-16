package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
public class JdbcWalletRepository implements WalletRepository{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert walletInserter;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.walletInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Wallets")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Wallet findUsersWallet(String userName) {
        return jdbcTemplate.queryForObject("SELECT id, walletName, walletType, createDate FROM Wallets WHERE userName = ?", this::mapRowToWallet, userName);
    }

    private Wallet mapRowToWallet(ResultSet rs, int rowNum) throws SQLException {
        Wallet wallet = new Wallet();

        wallet.setId(rs.getLong("id"));
        wallet.setWalletName(rs.getString("walletName"));
        wallet.setWalletType(Wallet.WalletType.valueOf(rs.getString("walletType")));
        wallet.setCreateDate(LocalDateTime.of(rs.getDate("createDate").toLocalDate(), rs.getTime("createDate").toLocalTime()));

        return wallet;
    }
}
