package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Person;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AccountService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PersonService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.validator.AccountValidator;
import it.uniroma3.siw.validator.CredentialsValidator;
import it.uniroma3.siw.validator.PersonValidator;
import it.uniroma3.siw.validator.UserValidator;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

	@Autowired
	private AccountService accountService;

    @Autowired
	private PersonService personService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private PersonValidator personValidator;

	@GetMapping(value = "/register")
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new Person());
		model.addAttribute("credentials", new Account());
		return "formRegisterUser";
	}

	@GetMapping(value = "/login")
	public String showLoginForm (Model model) {
		return "formLogin";
	}

	@GetMapping(value = "/")
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Account account = accountService.getAccount(userDetails.getUsername());
			if (account.getRole().equals(Account.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			}
		}
        return "index.html";
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

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute Person person, BindingResult personBindingResult,
    							@Valid @ModelAttribute Account account, BindingResult accountBindingResult, Model model) {
		this.accountValidator.validate(account, accountBindingResult);
		this.personValidator.validate(person, personBindingResult);

		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        if(!personBindingResult.hasErrors() && !accountBindingResult.hasErrors()) {
            personService.saveUser(person);
            account.setUser(person);
            accountService.saveCredentials(account);
            model.addAttribute("user", person);
            return "registrationSuccessful";
        }
        return "formRegisterUser.html";
    }
}