package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.model.Person;
import it.uniroma3.siw.service.AccountService;
import it.uniroma3.siw.service.CarService;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.service.PersonService;
import it.uniroma3.siw.validator.PersonValidator;
import jakarta.validation.Valid;

@Controller
public class PersonController {

	@Autowired
	 private PersonValidator personValidator;

	@Autowired
	private PersonService personService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private OrdineService ordineService;

	@Autowired
	private CarService carService;

	@GetMapping(value = "/profile")
	public String profilePage(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountService.getAccountByUsername(userDetails.getUsername());

		model.addAttribute("numeroOrdini", this.ordineService.getTotalOrders(account));
		model.addAttribute("numeroCar", this.carService.getTotalCars(account));
		model.addAttribute("person", account.getPerson());
		model.addAttribute("cars", this.carService.getAllCars(account));
		return "/user/profile.html";
	}

	@GetMapping(value = "/profile/edit")
	public String formProfileEdit(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("person", this.personService.getPerson(this.accountService.getAccountByUsername(userDetails.getUsername()).getPerson().getId()));
		return "/user/profileEdit.html";
	}

	@PostMapping(value = "/profile/edit")
	public String profileEdit(@Valid @ModelAttribute Person person, BindingResult bindingResult, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Account account = this.accountService.getAccountByUsername(userDetails.getUsername());

		person.setAccount(account);
		person.setId(account.getPerson().getId());

		this.personValidator.validate(person, bindingResult);

		if(!bindingResult.hasErrors()) {
			this.personService.savePerson(person);
			return "redirect:/profile";
		}

		return "/user/profileEdit.html";
	}
}