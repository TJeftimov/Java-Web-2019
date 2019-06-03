package hr.java.web.jeftimov.moneyapp.Controllers;

import hr.java.web.jeftimov.moneyapp.Entities.Expense;
import hr.java.web.jeftimov.moneyapp.Entities.Wallet;
import hr.java.web.jeftimov.moneyapp.Repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/expenses")
@SessionAttributes({"expenseTypes", "wallet"})
public class ExpenseController {

	private final ExpenseRepository expenseRepository;
	private final WalletRepository walletRepository;

	public ExpenseController(ExpenseRepository expenseRepository, WalletRepository walletRepository) {
		this.expenseRepository = expenseRepository;
		this.walletRepository = walletRepository;
	}

	@ModelAttribute("wallet")
	public Wallet createWallet(Authentication authentication) {
		// return hibernateWalletRepository.findUsersWallet(authentication.getName());
		return walletRepository.findByUsernameEquals(authentication.getName());
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

		expense.setWallet(wallet);
		expense.setCreatedate(LocalDateTime.now());
		expenseRepository.save(expense);
		// hibernateExpenseRepository.save(expense, wallet);
		wallet.getExpenseList().add(expense);

		log.info("Passing form result and adding expense to wallet");
		model.addAttribute("expense", expense);

		log.info("Passing sum of expenses in wallet");
		model.addAttribute("sumOfExpenses", wallet.sumOfExpenses());
		
		return "formResult";
	}

	@GetMapping("/resetWallet")
	public String resetWallet(SessionStatus status, Wallet wallet){
		expenseRepository.deleteAll(expenseRepository.findByWallet(wallet));
		log.info("Clearing wallet");
		status.setComplete();
		return "redirect:/expenses/new";
	}

	@GetMapping("/about")
	public String about(Model model) {
		return "about";
	}
}
