package dycs.billing.domain.abstractions;

import dycs.common.domain.valueobject.Money;

public interface PurchaseReportFactoryAbstract {
    PurchaseReport createFailedPurchase();
    PurchaseReport createNotSignedIn();
    PurchaseReport createNotRegistered(String userName);
    PurchaseReport createProductNotFound(String userName, String productName);
    PurchaseReport createNotEnoughMoney(String userName, String productName, Money price);
    PurchaseReport createReport(String userName, String productName, Money price);
}