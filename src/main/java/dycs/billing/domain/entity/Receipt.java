package dycs.billing.domain.entity;

import dycs.billing.domain.abstractions.PurchaseReport;
import dycs.common.domain.valueobject.Money;

public class Receipt implements PurchaseReport {
	private String userName;
    private String productName;
    private Money price;
    
    public Receipt(String userName, String productName, Money price)
    {
        this.userName = userName;
        this.productName = productName;
        this.price = price;
    }
    
    public String toUiText() {
        return String.format("Dear {%s},\nThank you for buying {%s} for {%.2f}.", this.userName, this.productName, this.price.getAmount());
    }
}
