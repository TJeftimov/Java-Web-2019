package hr.java.web.jeftimov.moneyapp.Controllers;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.User;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import hr.java.web.jeftimov.moneyapp.Repositories.JdbcExpenseRepository;
import hr.java.web.jeftimov.moneyapp.Repositories.JdbcUserRepository;
import hr.java.web.jeftimov.moneyapp.Repositories.JdbcWalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/expenses")
@SessionAttributes({"expenseTypes", "wallet"})
public class ExpenseController {

	JdbcExpenseRepository jdbcExpenseRepository;
	JdbcWalletRepository jdbcWalletRepository;
	public ExpenseController(JdbcExpenseRepository jdbcExpenseRepository, JdbcWalletRepository jdbcWalletRepository) {
		this.jdbcExpenseRepository = jdbcExpenseRepository;
		this.jdbcWalletRepository = jdbcWalletRepository;
	}

	@ModelAttribute("wallet")
	public Wallet createWallet(Authentication authentication) {
		return jdbcWalletRepository.findUsersWallet(authentication.getName());
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		return "index";
	}

	@GetMapping("/new")
	public String showForm(Model model) {
		log.info("Filling form with objects");
		model.addAttribute("expense", new Expense());
		model.addAttribute("expenseTypes", Expense.Type.values());
		
		return "newExpense";
	}
	
	@PostMapping("/new")
	public String processForm(@Validated Expense expense, Errors errors, Model model, Wallet wallet) {
		log.info("Checking if form is valid");
		if (errors.hasErrors()) {
			return "newExpense";
		}

		jdbcExpenseRepository.save(expense, wallet.getId());

		log.info("Passing form result and adding expense to wallet");
		model.addAttribute("expense", expense);
		wallet.setExpenseList(new ArrayList<>(jdbcExpenseRepository.getExpensesList(wallet)));

		log.info("Passing sum of expenses in wallet");
		model.addAttribute("sumOfExpenses", wallet.sumOfExpenses());
		
		return "formResult";
	}

	@GetMapping("/resetWallet")
	public String resetWallet(SessionStatus status){
		log.info("Clearing wallet");
		status.setComplete();
		return "redirect:/expenses/new";
	}

	@GetMapping("/about")
	public String about(Model model) {
		return "about";
	}
}
