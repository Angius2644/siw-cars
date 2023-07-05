package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.model.Car;
import it.uniroma3.siw.model.Person;
import it.uniroma3.siw.repository.AccountRepository;
import it.uniroma3.siw.repository.CarRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CarRepository carRepository;

	public Account getAccount(String username) {

		return this.accountRepository.findByUsername(username).get();
	}

	public Person getPerson(Long id) {

		return this.accountRepository.findById(id).get().getPerson();
	}

	public List<Car> getCars(Long id){

		return this.carRepository.carList(id);
	}
}
