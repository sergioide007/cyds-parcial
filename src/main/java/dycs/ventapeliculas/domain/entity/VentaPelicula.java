package dycs.ventapeliculas.domain.entity;

import dycs.common.application.notification.Notification;
import dycs.common.domain.valueobject.MoneyAbstraction;
import dycs.customers.domain.entity.Customer;
import dycs.pelicula.domain.entity.Pelicula;

public class VentaPelicula {
	private long id;
	private String number;
	private MoneyAbstraction importe;
	private boolean isLocked;
	private Customer customer;
	private Pelicula pelicula;
	
	public VentaPelicula() {
    }

    public void withdrawMoney(MoneyAbstraction amount) {
    	Notification notification = this.withdrawValidation(amount);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        this.importe = this.importe.subtract(amount);
    }

    public void depositMoney(MoneyAbstraction amount) {
    	Notification notification = this.depositValidation(amount);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        this.importe = this.importe.add(amount);
    }
    
    public Notification withdrawValidation(MoneyAbstraction amount) {
    	Notification notification = new Notification();
        this.validateAmount(notification, amount);
        this.validateBankAccount(notification);
        this.validateImporte(notification, amount);
        return notification;
    }
    
    public Notification depositValidation(MoneyAbstraction amount) {
        Notification notification = new Notification();
        this.validateAmount(notification, amount);
        this.validateBankAccount(notification);
        return notification;
    }
    
    private void validateAmount(Notification notification, MoneyAbstraction amount) {
        if (amount == null || amount.getAmount() == null) {
            notification.addError("amount is missing");
            return;
        }
        if (amount.getAmount().doubleValue() <= 0.0) {
            notification.addError("The amount must be greater than zero");
        }
    }
    
    private void validateBankAccount(Notification notification) {
        if (!this.hasIdentity()) {
            notification.addError("The account has no identity");
        }
        if (this.isLocked) {
        	notification.addError("The account is locked");
        }
    }
    
    private void validateImporte(Notification notification, MoneyAbstraction amount) {
        if (this.importe == null) {
            notification.addError("Importe cannot be null");
        }
        if (!this.canBeWithdrawed(amount)) {
        	notification.addError("Cannot withdraw in the account, amount is greater than importe");
        }
    }

    public boolean canBeWithdrawed(MoneyAbstraction amount) {
        return !this.isLocked && (this.importe.compareTo(amount) >= 0);
    }
    
    public void lock() {
        if (!this.isLocked) {
            this.isLocked = true;
        }
    }

    public void unLock() {
        if (this.isLocked) {
            this.isLocked = false;
        }
    }

    public boolean hasIdentity() {
        return this.id > 0 && !this.number.trim().equals("");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public MoneyAbstraction getImporte() {
        return this.importe;
    }

    public void setImporte(MoneyAbstraction importe) {
        this.importe = importe;
    }

    public boolean getIsLocked() {
        return isLocked;
    }
    
    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

	public Pelicula getPelicula() {
		return pelicula;
	}

	public void setPelicula(Pelicula pelicula) {
		this.pelicula = pelicula;
	}
    
    
}
