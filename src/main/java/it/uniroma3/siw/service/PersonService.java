package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import it.uniroma3.siw.model.Person;
import it.uniroma3.siw.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Transactional
	public Person getPerson(Long id) {
		return this.personRepository.findById(id).get();
	}

	@Transactional
	public Person savePerson(Person person) {
		person.setNome(StringUtils.capitalize(person.getNome()));
		person.setCognome(StringUtils.capitalize(person.getCognome()));
		person.setLuogoNascita(StringUtils.capitalize(person.getLuogoNascita()));

		this.personRepository.save(person);

		return person;
	}

	public List<Person> searchPeople(String nome, String cognome) {
		nome = StringUtils.capitalize(nome);
		cognome = StringUtils.capitalize(cognome);
		if (nome.isBlank() || cognome.isBlank()) {
			return this.personRepository.findByNomeOrCognome(nome, cognome);
		} else {
			return this.personRepository.findByNomeAndCognome(nome, cognome);
		}
	}

}
