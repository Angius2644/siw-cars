package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.service.OrdineService;

@Component
public class OrdineValidator implements Validator {

	@Autowired
	private OrdineService ordineService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Ordine.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Ordine ordine = (Ordine) o;

		if(ordine.getAccount() != null && ordine.getCar() != null && !ordine.getAccount().getCars().contains(ordine.getCar())) {
			errors.reject("ordine.invalidCarOwner");
		}

		if(this.ordineService.isPresent(ordine)) {
			errors.reject("ordine.duplicate");
		}
	}
}
