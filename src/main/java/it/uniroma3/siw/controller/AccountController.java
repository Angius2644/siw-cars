package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.model.Person;
import it.uniroma3.siw.repository.AccountRepository;
import it.uniroma3.siw.service.AccountService;
import jakarta.validation.Valid;

@Controller
public class AccountController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	private AccountService accountService;

	@GetMapping(value = "/formLogin")
	public String formLogin(Model model) {
		model.addAttribute("account", new Account());
		return "formLogin.html";
	}

	@PostMapping(value = "/login")
	public String requestedAccess(@Valid @ModelAttribute Account account, BindingResult bindingResult,
			Model model, @RequestParam String formAction) {
		if (formAction.equals("signIn")) {
			// Stringa da mettere nel validatore
			// this.accountRepository.existsByUsernameAndPassword(account.getUsername(),
			// account.getPassword())
			// model.addAttribute("messaggioErrore", "Utente non trovato, perfavore
			// registrati prima di eseguire l'accesso");
			if (!bindingResult.hasErrors()) {
				model.addAttribute("account",
						this.accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()));
				return "userPanel.html";
			}
		} else if (formAction.equals("signUp")) {
			// Stringa da mettere nel validatore
			// this.accountRepository.existsByUsername(account.getUsername())
			// model.addAttribute("messaggioErrore", "Esiste già un account con questo
			// Username, riprova.")
			if (!bindingResult.hasErrors()) {
				this.accountRepository.save(account);
				model.addAttribute("account", this.accountRepository.findById(account.getId()).get());
				return "userPanel.html";
			}
		} else {
			model.addAttribute("messaggioErrore", "Qualcosa è andato storto durante la richiesta");
		}
		return "formLogin.html";
	}

	@GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {

    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Account account = accountService.getAccount(userDetails.getUsername());
    	if (account.getRole().equals(Account.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
        return "index.html";
    }

	@GetMapping(value = "/accounts")
	public String allAccounts(Model model) {
		model.addAttribute("account", this.accountRepository.findAll());
		return "accounts.html";
	}

	@GetMapping(value = "/account/{idAccount}")
	public String accountDetail(@PathVariable("idAccount") Long idaccount, Model model) {

		model.addAttribute("account", this.accountRepository.findById(idaccount).get());
		return "accountDetail.html";
	}

	@GetMapping(value = "/addPersonToAccount/{idAccount}")
	public String addPersonToAccount(@PathVariable("idAccount") Long idaccount, Model model) {

		Account temp = this.accountRepository.findById(idaccount).get();
		temp.setPerson(new Person());
		this.accountRepository.save(temp);
		model.addAttribute("person", temp.getPerson());

		return "formNewPerson.html";
	}

	@GetMapping(value = "/user/{iD}/userPanel")
	public String userPanel(@PathVariable("iD") Long id, Model model) {
		model.addAttribute("cars", this.accountService.getCars(id));
		model.addAttribute("person", this.accountService.getPerson(id));
		return "userPanel.html";
	}
}