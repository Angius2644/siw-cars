package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

	public List<Car> findByTarga(String targa);

	public boolean existsByTarga(String targa);

	@Query("SELECT c FROM Car c WHERE c.account.id = ?1")
    public List<Car> carList(Long id);
}