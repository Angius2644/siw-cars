package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String numeroOrdine;

	@PositiveOrZero
	private float costo;

	@NotBlank
	private String titolo;

	@PastOrPresent
	private LocalDate dataCreazione;

	@NotBlank
	@Column(length = 2500)
	@Size(max = 2501)
	private String descrizione;

	@Column(length = 2500)
	@Size(max = 2501)
	private String risposta;

	private boolean isComplete;

	@ManyToOne
	private Account account;

	@ManyToOne
	private Car car;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroOrdine() {
		return numeroOrdine;
	}

	public void setNumeroOrdine(String numeroOrdine) {
		this.numeroOrdine = numeroOrdine;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataCreazione, numeroOrdine);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Ordine other = (Ordine) obj;
		return Objects.equals(dataCreazione, other.dataCreazione) && Objects.equals(numeroOrdine, other.numeroOrdine);
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public String numeroOrdineGenerator() {

	    // Ottengo l'anno, mese e giorno dalla data di creazione
	    String anno = String.valueOf(dataCreazione.getYear());
	    String mese = String.format("%02d", dataCreazione.getMonthValue());
	    String giorno = String.format("%02d", dataCreazione.getDayOfMonth());

	    // Ottengo l'ID dell'Account con zeri aggiuntivi se necessario
	    String idAccount = String.format("%04d", account.getId());

	    // Genero quattro cifre casuali per l'ID dell'ordine
	    String idOrdine = generateRandomDigits(4);

	    // Combino tutte le parti per formare il numero dell'ordine
	    String numeroOrdine = anno + "-" + mese + giorno + idAccount + idOrdine;

	    return numeroOrdine;
	}

	private String generateRandomDigits(int length) {
	    Random random = new Random();
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	        sb.append(random.nextInt(10));
	    }
	    return sb.toString();
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getRisposta() {
		return risposta;
	}

	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
}
