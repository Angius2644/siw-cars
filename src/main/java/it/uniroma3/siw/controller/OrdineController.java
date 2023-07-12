package it.uniroma3.siw.controller;

import java.time.LocalDate;

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
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.service.AccountService;
import it.uniroma3.siw.service.CarService;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.validator.OrdineValidator;
import jakarta.validation.Valid;

@Controller
public class OrdineController {
	@Autowired
	private OrdineValidator ordineValidator;

	@Autowired
	private OrdineService ordineService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private CarService carService;

	@GetMapping(value = "/newOrder")
	public String newOrderPage(Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountService.getAccountByUsername(userDetails.getUsername());

		if(this.accountService.hasCars(account.getUsername())) {
			model.addAttribute("ordine", new Ordine());
			model.addAttribute("cars", account.getCars());
			return "/user/order/formNewOrder.html";
		}
		model.addAttribute("person", this.accountService.getAccountByUsername(userDetails.getUsername()).getPerson());
		return "/user/order/noCarFound.html";
	}

	@PostMapping(value = "/newOrder")
	public String newOrder(@Valid @ModelAttribute Ordine ordine, BindingResult bindingResult, Model model, @RequestParam("car_id") Long car_id) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountService.getAccountByUsername(userDetails.getUsername());

		ordine.setAccount(account);
		ordine.setDataCreazione(LocalDate.now());
		ordine.setComplete(false);
		ordine.setCar(this.carService.getCar(car_id));
		ordine.setNumeroOrdine(ordine.numeroOrdineGenerator());
		ordine.setCosto(0);

		this.ordineValidator.validate(ordine, bindingResult);

		if(!bindingResult.hasErrors()) {
			this.ordineService.saveOrder(ordine);
			ordine.getCar().getOrdine().add(ordine);
			return "redirect:/myGarage";
		}

		model.addAttribute("cars", account.getCars());
		return "/user/order/formNewOrder.html";
	}

	@GetMapping(value = "/myGarage")
	public String myGaragePage(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("ordini", this.ordineService.getOrdini(this.accountService.getAccountByUsername(userDetails.getUsername())));
		return "/user/myGarage.html";
	}

	@GetMapping(value = "/deleteOrder/{order_id}")
	public String deleteOrder(@PathVariable Long order_id) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(this.accountService.getAccountByUsername(userDetails.getUsername()).getId().equals(this.ordineService.getOrdine(order_id).get().getAccount().getId())) {
			this.ordineService.deleteOrdine(order_id);
		}
		return "redirect:/myGarage";
	}

	@GetMapping(value = "/orderDetails/{order_id}")
	public String orderDetailsPage(@PathVariable Long order_id, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(this.accountService.getAccountByUsername(userDetails.getUsername()).getId().equals(this.ordineService.getOrdine(order_id).get().getAccount().getId())) {
			model.addAttribute("ordine", this.ordineService.getOrdine(order_id).get());
			return "/user/order/orderDetails.html";
		}
		return "redirect:/myGarage";
	}

	@PostMapping(value = "/searchOrders")
	public String searchOrders(@RequestParam("search") String ricerca, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("ordini", this.ordineService.searchOrders(this.accountService.getAccountByUsername(userDetails.getUsername()).getId(), ricerca));
		return "/user/myGarage.html";
	}

	@PostMapping(value = "/admin/searchOrders/{condition}")
	public String adminSearchOrders(@RequestParam("search") String ricerca, @PathVariable boolean condition, Model model) {

		model.addAttribute("ordini", this.ordineService.adminSearchOrders(ricerca, condition));
		return "/admin/checkOrders.html";
	}

	@GetMapping(value = "/admin/checkUndoneOrders")
	public String checkUndoneOrders(Model model) {

		model.addAttribute("condition", false);
		model.addAttribute("ordini", this.ordineService.listStatusOrders(false));
		return "/admin/checkOrders.html";
	}

	@GetMapping(value = "/admin/checkDoneOrders")
	public String checkDoneOrders(Model model) {

		model.addAttribute("condition", true);
		model.addAttribute("ordini", this.ordineService.listStatusOrders(true));
		return "/admin/checkOrders.html";
	}

	@GetMapping(value = "/admin/orderDetails/{order_id}")
	public String adminOrderDetailsPage(@PathVariable Long order_id, Model model) {

		model.addAttribute("ordine", this.ordineService.getOrdine(order_id).get());
		return "/admin/order/orderDetails.html";
	}

	@GetMapping(value = "/admin/deleteOrder/{order_id}")
	public String adminDeleteOrder(@PathVariable Long order_id) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		this.ordineService.deleteOrdine(order_id);
		if(this.accountService.getAccountByUsername(userDetails.getUsername()).getRole().equals(Account.ADMIN_ROLE)) {
			return "redirect:/";
		}
		return "redirect:/myGarage";
	}

	@GetMapping(value = "/admin/addAnswer/{order_id}")
	public String addAnswerToOrder(@PathVariable Long order_id, Model model) {
		model.addAttribute("ordine", this.ordineService.getOrdine(order_id).get());
		return "/admin/order/addAnswerToOrder.html";
	}

	@PostMapping(value = "/admin/addAnswerToOrder/{order_id}")
	public String addAnswerToOrderPost(@PathVariable Long order_id, @RequestParam("costo") float costo, @RequestParam("descrizione") String risposta, Model model) {
		Ordine order = this.ordineService.getOrdine(order_id).get();
		if(costo < 0 || risposta.length() > 1500 || risposta.isBlank()) {
			if(costo < 0) {
				model.addAttribute("errorCosto", "Il costo deve essere positivo o uguale a zero");
			} if(risposta.length() > 1500 || risposta.isBlank()) {
				model.addAttribute("errorRisposta", "La risposta è troppo lunga o vuota");
			}
			model.addAttribute("ordine", this.ordineService.getOrdine(order_id).get());
			return "/admin/order/addAnswerToOrder.html";
		}

			order.setCosto(costo);
			order.setRisposta(risposta);
			order.setComplete(true);
			this.ordineService.saveOrder(order);
			return "redirect:/admin/orderDetails/" + order_id;
	}

}
