package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import it.uniroma3.siw.model.Car;
import it.uniroma3.siw.repository.AccountRepository;
import it.uniroma3.siw.repository.CarRepository;

@Service
public class CarService {

	@Autowired private CarRepository carRepository;
	@Autowired private AccountRepository accountRepository;

	public List<Car> carListById(Long id) {
		return this.carRepository.carList(id);
	}

	public Car newCar (Car car) {
		car.setTarga(car.getTarga().toUpperCase());
		car.setColore(StringUtils.capitalize(car.getColore()));
		car.setMarca(StringUtils.capitalize(car.getMarca()));
		car.setModello(StringUtils.capitalize(car.getModello()));

		this.carRepository.save(car);

		return car;
	}

	public Car formNewCar(Long id) {
		Car car = new Car();
		car.setAccount(this.accountRepository.findById(id).get());
		return car;
	}

	public Car getCar(Long id) {
		return this.carRepository.findById(id).get();
	}

	public List<Car> searchCar(String targa){
		return this.carRepository.findByTarga(targa.toUpperCase());
	}

	public Iterable<Car> showCar(){
		return this.carRepository.findAll();
	}

	public Iterable<Car> userCars(Long id){
		if(this.accountRepository.findById(id).get() != null)
		return this.carRepository.carList(id);
		else
			return null;
	}
}
