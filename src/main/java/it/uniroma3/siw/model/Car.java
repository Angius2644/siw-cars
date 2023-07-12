package it.uniroma3.siw.model;

import java.time.Year;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

@Entity
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Pattern(regexp = "^[A-Za-z]{2}\\d{3}[A-Za-z]{2}$")
	@NotBlank
	private String targa;

	@NotBlank
	private String marca;

	@NotBlank
	private String modello;

	@Min(0)
	@Digits(integer = 7, fraction = 0)
	private Long chilometri;

	@PastOrPresent
	private Year immatricolazione;

	private String colore;

	@ManyToOne
	private Account account;

	@OneToMany(mappedBy = "car", cascade = {CascadeType.REMOVE})
	private List<Ordine> ordine;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public Long getChilometri() {
		return chilometri;
	}

	public void setChilometri(Long chilometri) {
		this.chilometri = chilometri;
	}

	public Year getImmatricolazione() {
		return immatricolazione;
	}

	public void setImmatricolazione(Year immatricolazione) {
		this.immatricolazione = immatricolazione;
	}

	public String getColore() {
		return colore;
	}

	public void setColore(String colore) {
		this.colore = colore;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		return Objects.hash(immatricolazione, marca, modello, targa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Car other = (Car) obj;
		return Objects.equals(immatricolazione, other.immatricolazione) && Objects.equals(marca, other.marca)
				&& Objects.equals(modello, other.modello) && Objects.equals(targa, other.targa);
	}

	public List<Ordine> getOrdine() {
		return ordine;
	}

	public void setOrdine(List<Ordine> ordine) {
		this.ordine = ordine;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", targa=" + targa + ", marca=" + marca + ", modello=" + modello + ", chilometri="
				+ chilometri + ", immatricolazione=" + immatricolazione + ", colore=" + colore + ", account=" + account
				+ ", ordine=" + ordine + "]";
	}
}
