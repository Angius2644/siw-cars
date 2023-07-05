package it.uniroma3.siw.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
//import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Person;
import it.uniroma3.siw.repository.PersonRepository;

@Component
public class PersonValidator implements Validator {

	@Autowired
	private PersonRepository personRepository;

	@Override
	public boolean supports(Class<?> aClass) {
		return Person.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {

		Person person = (Person) o;
		if (person.getDataNascita() != null && person.getDataNascita().isBefore(LocalDate.of(1900, 1, 1))) {
			errors.reject("person.minDateNotValid");
		}
		if (person.getDataNascita() != null
				&& this.personRepository.existsByNomeAndCognomeAndDataNascitaAndLuogoNascita(person.getNome(),
						person.getCognome(), person.getDataNascita(), person.getLuogoNascita())) {
			errors.reject("person.duplicate");
		}
	}

}
