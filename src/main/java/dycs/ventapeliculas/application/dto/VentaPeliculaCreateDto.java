package dycs.ventapeliculas.application.dto;

import java.math.BigDecimal;

public class VentaPeliculaCreateDto {
	private String number;
	private BigDecimal balance;
	private String currency;
	private boolean locked = false;
	private long customerId;
	private long peliculaId;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getPeliculaId() {
		return peliculaId;
	}

	public void setPeliculaId(long peliculaId) {
		this.peliculaId = peliculaId;
	}
	
	
}
