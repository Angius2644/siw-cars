package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.model.Car;
import it.uniroma3.siw.service.AccountService;
import it.uniroma3.siw.service.CarService;
import it.uniroma3.siw.validator.CarValidator;
import jakarta.validation.Valid;

@Controller
public class CarController {

	@Autowired
	private CarValidator carValidator;

	@Autowired
	private CarService carService;

	@Autowired
	private AccountService accountService;


	@GetMapping(value = "/newCar")
	public String formNewCar(Model model) {
		model.addAttribute("car", new Car());
		return "/user/car/formNewCar.html";
	}

	@PostMapping(value = "/newCar")
	public String newCar(@Valid @ModelAttribute Car car, BindingResult bindingResult, Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		car.setTarga(car.getTarga().toUpperCase());
		car.setColore(StringUtils.capitalize(car.getColore()));
		car.setMarca(StringUtils.capitalize(car.getMarca()));
		car.setModello(StringUtils.capitalize(car.getModello()));
		car.setAccount(this.accountService.getAccountByUsername(userDetails.getUsername()));

		this.carValidator.validate(car, bindingResult);

		if(!bindingResult.hasErrors()) {
			model.addAttribute("car", this.carService.saveCar(car));
			return "/user/car/newCarSucces.html";
		}

		return "/user/car/formNewCar.html";
	}

	@GetMapping(value = "/carDetails/{car_id}")
	public String carDetailsPage(@PathVariable Long car_id, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(this.accountService.getAccountByUsername(userDetails.getUsername()).getCars().contains(this.carService.getCar(car_id))) {
			model.addAttribute("car", this.carService.getCar(car_id));
			return "/user/car/carDetails.html";
		}
		return "redirect:/profile";
	}

	@GetMapping(value = "/updateCarDetails/{car_id}")
	public String updateCarDetailsPage(@PathVariable Long car_id, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(this.accountService.getAccountByUsername(userDetails.getUsername()).getCars().contains(this.carService.getCar(car_id))) {
			model.addAttribute("car", this.carService.getCar(car_id));
			return "/user/car/formUpdateCarDetails.html";
		}
		return "redirect:/carDetails/" + car_id;
	}

	@PostMapping(value = "/updateCarDetails/{car_id}")
	public String updateCarDetails(@Valid @ModelAttribute Car car, BindingResult bindingResult, Model model, @PathVariable Long car_id) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Account account = this.accountService.getAccountByUsername(userDetails.getUsername());

		car.setAccount(account);
		car.setId(car_id);
		car.setOrdine(this.carService.getCar(car_id).getOrdine());

		if(account.getCars().contains(this.carService.getCar(car_id))) {
			this.carService.saveCar(car);
			return "redirect:/carDetails/" + car_id;
		}
		return "redirect:/carDetails/" + car_id;
	}

	@GetMapping(value = "/deleteCar/{car_id}")
	public String deleteCar(@PathVariable Long car_id, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(this.accountService.getAccountByUsername(userDetails.getUsername()).getCars().contains(this.carService.getCar(car_id))) {
			this.carService.deleteCar(car_id);
		}
		return "redirect:/profile";
	}

}