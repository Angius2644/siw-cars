package it.uniroma3.siw.validator;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Car;
import it.uniroma3.siw.repository.CarRepository;

@Component
public class CarValidator implements Validator {
	@Autowired
	private CarRepository carRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return Car.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Car car = (Car) o;
		if (this.carRepository.existsByTarga(car.getTarga())) {
			errors.reject("car.duplicate");
		}
		if (car.getImmatricolazione() != null && car.getImmatricolazione().isBefore(Year.of(1926))) {
			errors.reject("Min.car.immatricolazione");
		}
		if (car.getImmatricolazione() != null && car.getImmatricolazione().length() != 4) {
			errors.reject("lengthMismatch.car.immatricolazione");
		}
	}
}
