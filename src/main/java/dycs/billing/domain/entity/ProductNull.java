package dycs.billing.domain.entity;

import org.springframework.stereotype.Component;
import dycs.common.domain.valueobject.Money;

@Component
public class ProductNull extends Product {
    public ProductNull() {
        this.name = "";
        this.price = new Money(0, "USD");
    }
    
    @Override
	public boolean isNull() {
    	return true;
    }
}
