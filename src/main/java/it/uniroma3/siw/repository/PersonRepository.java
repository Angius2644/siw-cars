package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

	public List<Person> findByNomeAndCognome(String nome, String cognome);

	public List<Person> findByNomeOrCognome(String nome, String cognome);

	public boolean existsByNomeAndCognomeAndDataNascitaAndLuogoNascita(String nome, String cognome,
			LocalDate dataNascita, String luogoNascita);

	public List<Person> findByTelefono(Long telefono);
}