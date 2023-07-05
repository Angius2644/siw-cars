package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Person;
import it.uniroma3.siw.service.PersonService;
import it.uniroma3.siw.validator.PersonValidator;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class PersonController {
	@Autowired
	PersonValidator personValidator;
	@Autowired
	PersonService personService;

	@GetMapping(value = "/formNewPerson")
	public String formNewPerson(Model model) {
		model.addAttribute("person", new Person());
		return "formNewPerson.html";
	}

	@GetMapping(value = "/people")
	public String showPeople(Model model) {
		model.addAttribute("people", this.personService.showPeople());
		return "people.html";
	}

	@Transactional
	@PostMapping(value = "/people")
	public String newPerson(@Valid @ModelAttribute Person person, BindingResult bindingResult, Model model) {
		this.personValidator.validate(person, bindingResult);
		if (!bindingResult.hasErrors()) {
			model.addAttribute("person", person);
			return "person.html";
		}

		return "formNewPerson.html";
	}

	@GetMapping(value = "/person/{id}")
	public String getPerson(@PathVariable long id, Model model) {
		model.addAttribute("person", this.personService.getPerson(id));
		return "person.html";
	}

	@GetMapping(value = "/formSearchPerson")
	public String formSearchPerson() {
		return "formSearchPerson.html";
	}

	@PostMapping(value = "/searchPeople")
	public String searchPeople(Model model, @RequestParam String nome, @RequestParam String cognome) {
		model.addAttribute("people", this.personService.searchPeople(nome, cognome));
		model.addAttribute("nome", nome);
		model.addAttribute("cognome", cognome);
		return "foundPeople.html";
	}
}