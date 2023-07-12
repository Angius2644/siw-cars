package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.model.Car;
import it.uniroma3.siw.model.Ordine;

public interface OrdineRepository extends CrudRepository<Ordine, Long>{

	public List<Ordine> findByNumeroOrdine(String numeroOrdine);

	public List<Ordine> findByAccountOrderByDataCreazioneDesc(Account accout);

	public boolean existsByNumeroOrdine(String numeroOrdine);

	public boolean existsByTitoloAndDataCreazioneAndCar(String titolo, LocalDate dataCreazione, Car car);

	public int countByAccount(Account account);

	@Query("SELECT c FROM Ordine c WHERE c.account.id = ?1 AND (c.numeroOrdine = ?2 OR c.car.marca = ?2 OR c.car.targa = ?2) ORDER BY c.dataCreazione DESC")
	public List<Ordine> searchOrders(Long id, String ricerca);

	@Query("SELECT c FROM Ordine c WHERE c.isComplete = ?2 AND (c.numeroOrdine = ?1 OR c.car.marca = ?1 OR c.car.targa = ?1) ORDER BY c.dataCreazione DESC")
	public List<Ordine> adminSearchOrders(String ricerca, boolean condition);

	public List<Ordine> findAllByIsComplete(boolean condition);
}
