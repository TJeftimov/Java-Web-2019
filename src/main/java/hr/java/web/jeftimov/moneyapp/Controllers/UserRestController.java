package hr.java.web.jeftimov.moneyapp.Controllers;

import com.google.common.collect.Lists;
import hr.java.web.jeftimov.moneyapp.Entities.User;
import hr.java.web.jeftimov.moneyapp.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/user", produces="application/json")
@CrossOrigin
public class UserRestController {

    private final UserRepository userRepository;
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping
    public List<User> findAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable Long id) {
        User user =  userRepository.findById(id).get();

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes="application/json")
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        User usr = userRepository.findById(id).get();
        user.setId(id);
        return userRepository.save(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.delete(userRepository.findById(id).get());
    }
}
