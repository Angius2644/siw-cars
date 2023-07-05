package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.validator.OrdineValidator;
import jakarta.validation.Valid;

@Controller
public class OrdineController {
	@Autowired
	private OrdineValidator ordineValidator;
	@Autowired
	private OrdineService ordineService;

	@GetMapping(value = "/user/{id}/newOrder")
	public String newOrderPage(@PathVariable("id") Long id, Model model) {

		model.addAttribute("ordine", this.ordineService.newOrderPage(id));
		return "Ordine/newOrdine.html";
	}

	@PostMapping(value = "/user/{id}/newOrder")
	public String newOrder(@Valid @ModelAttribute Ordine ordine, BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
		this.ordineValidator.validate(ordine, bindingResult);
		if(!bindingResult.hasErrors()) {
			model.addAttribute("ordine", this.ordineService.newOrder(ordine, id));
			return "/Ordine/orderDetails.html";
		}
		return "Ordine/newOrdine.html";
	}
}
