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

import it.uniroma3.siw.model.Car;
import it.uniroma3.siw.service.CarService;
import it.uniroma3.siw.validator.CarValidator;
import jakarta.validation.Valid;

@Controller
public class CarController {
	@Autowired
	private CarValidator carValidator;
	@Autowired
	private CarService carService;

	@GetMapping(value = "/index.html")
	public String index() {
		return "index.html";
	}
	
	@GetMapping(value = "/")
	public String index2() {
		return "index.html";
	}

	@GetMapping(value = "/user/{iD}/formNewCar")
	public String formNewCar(@PathVariable("iD") Long id,Model model) {
		model.addAttribute("car", this.carService.formNewCar(id));
		return "formNewCar.html";
	}

	@PostMapping(value = "/cars")
	public String newCar(@Valid @ModelAttribute Car car, BindingResult bindingResult, Model model) {
		this.carValidator.validate(car, bindingResult);
		if (!bindingResult.hasErrors()) {
			model.addAttribute("car", this.carService.newCar(car));
			return "car.html";
		} else {

			return "formNewCar.html";
		}
	}

	@GetMapping(value = "/cars/{id}")
	public String getCar(@PathVariable Long id, Model model) {
		model.addAttribute("car", this.carService.getCar(id));
		return "car.html";
	}

	@GetMapping(value = "/cars")
	public String showCars(Model model) {
		model.addAttribute("cars", this.carService.showCar());
		return "cars.html";
	}

	@GetMapping(value = "/formSearchCars")
	public String formSearchCars() {
		return "formSearchCars.html";
	}

	@PostMapping(value = "/searchCars")
	public String searchCars(Model model, @RequestParam String targa) {
		if (targa != null) {
			model.addAttribute("cars", this.carService.searchCar(targa));
			model.addAttribute("targa", targa);
			return "foundCars.html";
		}
		return "formSearchCars.html";
	}
	@GetMapping(value = "/user/{iD}/cars")
	public String userPanel(@PathVariable("iD") Long id, Model model) {
		Iterable<Car> cars = this.carService.userCars(id);
		if(cars != null) {
			model.addAttribute("cars", this.carService.userCars(id));
			return "cars.html";}

		return "error.html";

	}

}