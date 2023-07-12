package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

	public boolean existsByUsername(String username);

	public Optional<Account> findByUsername(String username);

}