package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcExpenseRepository implements ExpenseRepository{

        private final JdbcTemplate jdbcTemplate;
        private final SimpleJdbcInsert expenseInserter;

        public JdbcExpenseRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            this.expenseInserter = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("Expenses")
                    .usingGeneratedKeyColumns("id");
        }

    @Override
    public List<Expense> findAll() {
        return jdbcTemplate.query("SELECT id, name, amount, type, createDate FROM Expenses", this::mapRowToExpense);
    }
    @Override
    public Expense findOne(Long id) {
        return jdbcTemplate.queryForObject("SELECT id, name, amount, type, createDate FROM Expenses WHERE id = ?", this::mapRowToExpense, id);
    }
    @Override
    public Expense save(Expense expense, Long walletId) {
        expense.setCreateDate(LocalDateTime.now());
        expense.setId(saveExpenseDetails(expense, walletId));

        return expense;
    }

    @Override
    public List<Expense> getExpensesList(Wallet wallet) {
        return jdbcTemplate.query("SELECT id, name, amount, type, createDate FROM Expenses WHERE walletId = ?", this::mapRowToExpense, wallet.getId());
    }

    private Long saveExpenseDetails(Expense expense, Long walletId) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", expense.getName());
        values.put("amount", expense.getAmount());
        values.put("type", expense.getType());
        values.put("createDate", expense.getCreateDate());
        values.put("walletId", walletId);
        return expenseInserter.executeAndReturnKey(values).longValue();

    }

    private Expense mapRowToExpense(ResultSet rs, int rowNum) throws SQLException {
        Expense expense = new Expense();

        expense.setId(rs.getLong("id"));
        expense.setName(rs.getString("name"));
        expense.setAmount(rs.getDouble("amount"));
        expense.setCreateDate(LocalDateTime.of(rs.getDate("createDate").toLocalDate(), rs.getTime("createDate").toLocalTime()));
        expense.setType(Expense.Type.valueOf(rs.getString("type")));

        return expense;
    }

}
