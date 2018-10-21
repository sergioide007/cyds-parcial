package dycs.billing.domain.entity;

import dycs.common.domain.valueobject.Money;

public class Product {
	protected String name;
    protected Money price;

    public Product() {
    }
    
    public Product(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
    
    public boolean isNull() {
    	return false;
    }
}
