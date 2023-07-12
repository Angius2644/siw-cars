package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.repository.AccountRepository;
import it.uniroma3.siw.repository.CarRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CarRepository carrepository;

	@Autowired
    protected PasswordEncoder passwordEncoder;

	@Transactional
	public Account getAccountByUsername(String username) {

		return this.accountRepository.findByUsername(username).get();
	}

	@Transactional
	public Account getAccount(Long account_id) {

		return this.accountRepository.findById(account_id).get();
	}

	@Transactional
	public Account saveAccount(Account account) {
		account.setRole(Account.DEFAULT_ROLE);
		account.setPassword(this.passwordEncoder.encode(account.getPassword()));
		return this.accountRepository.save(account);
	}

	@Transactional
	public String getRole(String username) {
		return this.accountRepository.findByUsername(username).get().getRole();
	}

	@Transactional
	public boolean isPresentByUsername(String username) {
		return this.accountRepository.existsByUsername(username);
	}

	@Transactional
	public boolean hasCars(String username) {
		return this.carrepository.existsByAccount(this.accountRepository.findByUsername(username).get());
	}
}
