package dycs.billing.domain.entity;

import dycs.billing.domain.abstractions.PurchaseReport;

public class ProductNotFound implements PurchaseReport {
    private String userName;
    private String productName;
    
    public ProductNotFound(String userName, String productName)
    {
        this.userName = userName;
        this.productName = productName;
    }
    
    public String toUiText() {
        return String.format("Dear {%s},\\nSorry to inform you that we have no {%s} left.", this.userName, this.productName);
    }
}