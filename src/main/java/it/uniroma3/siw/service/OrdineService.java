package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Account;
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
	public Ordine saveOrder(Ordine order) {
		return this.ordineRepository.save(order);
	}

	@Transactional
	public boolean isPresent(Ordine order) {
		return this.ordineRepository.existsByTitoloAndDataCreazioneAndCar(order.getTitolo(), order.getDataCreazione(), order.getCar());
	}

	@Transactional
	public Optional<Ordine> getOrdine(Long order_id) {
		return this.ordineRepository.findById(order_id);
	}

	@Transactional
	public List<Ordine> getOrdini(Account account) {
		return this.ordineRepository.findByAccountOrderByDataCreazioneDesc(account);
	}

	@Transactional
	public void deleteOrdine(Long order_id) {
		this.ordineRepository.deleteById(order_id);
	}

	@Transactional
	public int getTotalOrders(Account account) {
		return this.ordineRepository.countByAccount(account);
	}

	@Transactional
	public List<Ordine> searchOrders(Long account_id, String ricerca) {
		return this.ordineRepository.searchOrders(account_id, ricerca);
	}

	@Transactional
	public List<Ordine> adminSearchOrders(String ricerca, boolean condition) {
		return this.ordineRepository.adminSearchOrders(ricerca, condition);
	}

	@Transactional
	public List<Ordine> listStatusOrders(boolean status) {
		return this.ordineRepository.findAllByIsComplete(status);
	}
}
