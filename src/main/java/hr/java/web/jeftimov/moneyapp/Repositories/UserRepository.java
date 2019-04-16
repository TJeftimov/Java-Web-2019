package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    List<User> getAll();
    User save(User user) throws SQLException;
}
