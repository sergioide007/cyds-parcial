package dycs.billing.domain.entity;

import dycs.billing.domain.abstractions.PurchaseReport;

public class NotSignedIn implements PurchaseReport {
    @Override
    public String toUiText() {
        return "No user is signed in.";
    }
}