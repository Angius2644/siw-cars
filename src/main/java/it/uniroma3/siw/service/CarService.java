package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Account;
import it.uniroma3.siw.model.Car;
import it.uniroma3.siw.repository.CarRepository;
import jakarta.transaction.Transactional;

@Service
public class CarService {

	@Autowired
	private CarRepository carRepository;

	@Transactional
	public Car saveCar (Car car) {
		return this.carRepository.save(car);
	}

	@Transactional
	public Car getCar(Long id) {
		return this.carRepository.findById(id).get();
	}

	@Transactional
	public List<Car> getAllCars(Account account) {
		return this.carRepository.findAllByAccount(account);
	}

	@Transactional
	public int getTotalCars(Account account) {
		return this.carRepository.countByAccount(account);
	}

	@Transactional
	public void deleteCar(Long car_id) {
		this.carRepository.deleteById(car_id);
	}
}
