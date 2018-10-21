package dycs.billing.domain.entity;

import dycs.billing.domain.abstractions.PurchaseReport;

public class NotRegistered implements PurchaseReport {
    private String userName;
    
    public NotRegistered(String userName)
    {
        this.userName = userName;
    }
    
    public String toUiText() {
        return String.format("User {%s} is not registered.", this.userName);
    }
}