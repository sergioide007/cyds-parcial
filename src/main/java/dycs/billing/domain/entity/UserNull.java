package dycs.billing.domain.entity;

import org.springframework.stereotype.Component;
import dycs.billing.domain.abstractions.PurchaseReport;

@Component
public class UserNull extends User {
	public UserNull() {
		this.name = "";
	}
	
	@Override
	public PurchaseReport purchase(Product product)
	{
		return purchaseReportFactory.createNotRegistered(this.name);
	}
	
	@Override
	public boolean isLoggedIn() {
		return false;
	}
	
	@Override
	public boolean isNull() {
    	return true;
    }
}
