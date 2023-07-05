package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.repository.AccountRepository;
import it.uniroma3.siw.repository.OrdineRepository;
import jakarta.transaction.Transactional;

@Service
public class OrdineService {

	@Autowired private AccountRepository accountRepository;
	@Autowired private OrdineRepository ordineRepository;

	public Ordine newOrderPage(Long id) {
		Ordine ordine = new Ordine();
		ordine.setAccount(this.accountRepository.findById(id).get());
		return ordine;
	}

	@Transactional
	public Ordine newOrder(Ordine order, Long id) {
		order.setAccount(this.accountRepository.findById(id).get());
		this.ordineRepository.save(order);

		return order;
	}
}
