package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.repository.CarRepository;
import it.uniroma3.siw.repository.OrdineRepository;

@Component
public class OrdineValidator implements Validator {

	@Autowired OrdineRepository ordineRepository;
	@Autowired CarRepository carRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return Ordine.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Ordine ordine = (Ordine) o;

		if(this.ordineRepository.existsByTitoloAndIsComplete(ordine.getTitolo(), false)) {
			errors.reject("ordine.duplicate");
		}

		if(this.carRepository.carList(ordine.getId()).contains(ordine.getCar())) {
			errors.reject("ordine.mismatchCar");
		}
	}
}
