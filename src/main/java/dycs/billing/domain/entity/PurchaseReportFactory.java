package dycs.billing.domain.entity;

import org.springframework.stereotype.Component;

import dycs.ApplicationContextHolder;
import dycs.billing.domain.abstractions.PurchaseReport;
import dycs.billing.domain.abstractions.PurchaseReportFactoryAbstract;
import dycs.common.domain.valueobject.Money;

@Component
public class PurchaseReportFactory implements PurchaseReportFactoryAbstract {
	@Override
    public PurchaseReport createFailedPurchase() {
        return ApplicationContextHolder.getContext().getBean(FailedPurchase.class);
    }

    @Override
    public PurchaseReport createNotSignedIn() {
        return new NotSignedIn();
    }

    @Override
    public PurchaseReport createNotRegistered(String userName) {
        return new NotRegistered(userName);
    }

    @Override
    public PurchaseReport createProductNotFound(String userName, String productName) {
        return new ProductNotFound(userName, productName);
    }

    @Override
    public PurchaseReport createNotEnoughMoney(String userName, String productName, Money price) {
        return new NotEnoughMoney(userName, productName, price);
    }

    @Override
    public PurchaseReport createReport(String userName, String productName, Money price) {
        return new Receipt(userName, productName, price);
    }
}
