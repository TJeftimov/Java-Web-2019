package hr.java.web.jeftimov.moneyapp.Repositories;

import hr.java.web.jeftimov.moneyapp.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserRepository implements UserRepository{

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert userInserter;
    private final SimpleJdbcInsert roleInserter;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.userInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.roleInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("authorities");
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT username, password FROM users", this::mapRowToUser);
    }

    @Override
    public User save(User user) {
        List<User> userList = getAll();
        for (User u: userList) {
            if (u.getUsername().compareTo(user.getUsername()) == 0) {
                return null;
            }
        }
        Map<String, Object> userValues = new HashMap<>();
        userValues.put("username", user.getUsername());
        userValues.put("password", passwordEncoder.encode(user.getPassword()));
        userValues.put("enabled", true);

        Map<String, Object> roleValues = new HashMap<>();
        roleValues.put("username", user.getUsername());
        roleValues.put("authority", "ROLE_USER");

        userInserter.execute(userValues);
        roleInserter.execute(roleValues);

        return user;
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    }

}
