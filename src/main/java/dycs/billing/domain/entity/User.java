package dycs.billing.domain.entity;

import java.math.BigDecimal;
import dycs.ApplicationContextHolder;
import dycs.billing.domain.abstractions.PurchaseReport;

public class User {
	protected String name;
	double BALANCE = 22;
	
    protected PurchaseReportFactory purchaseReportFactory;
        
	public User() {
		purchaseReportFactory = ApplicationContextHolder.getContext().getBean(PurchaseReportFactory.class);
    }
	
	public User(String name) {		
        this.name = name;
        purchaseReportFactory = ApplicationContextHolder.getContext().getBean(PurchaseReportFactory.class);
    }
    
    public PurchaseReport purchase(Product product)
    {
    	BigDecimal balance = new BigDecimal(BALANCE);
    	if (product.getPrice().getAmount().compareTo(balance) == 1) {
    		return this.purchaseReportFactory.createNotEnoughMoney(this.name, product.getName(), product.getPrice());
    	}
    	return new Receipt(this.name, product.getName(), product.getPrice());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isLoggedIn() {
        return !this.name.isEmpty();
    }
    
    public boolean isNull() {
    	return false;
    }
}
