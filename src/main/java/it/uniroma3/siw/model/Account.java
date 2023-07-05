package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Account {
	
	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String username;

	@NotBlank
	private String password;
	private String role;

	@OneToOne/*(cascade = {CascadeType.ALL})*/ //Non lo utilizziamo così abbiamo comunque traccia di tutti gli ordini eseguiti e le persone corrispondenti
	private Person person;

	@OneToMany(mappedBy = "account")
	private List<Car> car;

	@OneToMany(mappedBy = "account")
	private List<Ordine> ordine;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String isAdmin() {
		return role;
	}

	public void setAdmin(String isAdmin) {
		this.role = isAdmin;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Car> getCar() {
		return car;
	}

	public void setCar(List<Car> car) {
		this.car = car;
	}

	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Account other = (Account) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Ordine> getOrdine() {
		return ordine;
	}

	public void setOrdine(List<Ordine> ordine) {
		this.ordine = ordine;
	}
}
