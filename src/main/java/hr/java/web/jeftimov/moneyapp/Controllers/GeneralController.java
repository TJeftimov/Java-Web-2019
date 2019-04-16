package hr.java.web.jeftimov.moneyapp.Controllers;

import hr.java.web.jeftimov.moneyapp.Entities.User;
import hr.java.web.jeftimov.moneyapp.Repositories.JdbcUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@Controller
public class GeneralController {

    JdbcUserRepository jdbcUserRepository;

    public GeneralController(JdbcUserRepository jdbcUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
    }

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userExists", "");
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(User user,Model model) {

        if(jdbcUserRepository.save(user) == null) {
            model.addAttribute("userExists", "User already exists");
            return "signup";
        }
        return "redirect:/expenses/home";
    }
}
