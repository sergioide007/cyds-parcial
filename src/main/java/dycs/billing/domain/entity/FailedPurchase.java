package dycs.billing.domain.entity;

import org.springframework.stereotype.Component;

import dycs.billing.domain.abstractions.PurchaseReport;

@Component
public class FailedPurchase implements PurchaseReport {
    @Override
    public String toUiText() {
        return "Purchase failed.";
    }
}
