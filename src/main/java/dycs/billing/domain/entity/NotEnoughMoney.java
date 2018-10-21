package dycs.billing.domain.entity;

import dycs.billing.domain.abstractions.PurchaseReport;
import dycs.common.domain.valueobject.Money;

public class NotEnoughMoney implements PurchaseReport {
    private String userName;
    private String productName;
    private Money price;
    
    public NotEnoughMoney(String userName, String productName, Money price)
    {
        this.userName = userName;
        this.productName = productName;
        this.price = price;
    }
    
    public String toUiText() {
        return String.format("Dear {%s},\nYou do not have {%.2f} to pay for the {%s}.", this.userName, this.price.getAmount(), this.productName);
    }
}
