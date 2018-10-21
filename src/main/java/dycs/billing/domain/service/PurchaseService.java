package dycs.billing.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dycs.billing.domain.abstractions.PurchaseReport;
import dycs.billing.domain.entity.Product;
import dycs.billing.domain.entity.PurchaseReportFactory;
import dycs.billing.domain.entity.User;
import dycs.billing.domain.repository.ProductRepository;
import dycs.billing.domain.repository.UserRepository;

@Service
public class PurchaseService {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PurchaseReportFactory purchaseReportFactory;
	
	public PurchaseReport purchase(String userName, String productName)
    {
        Product product = this.productRepository.find(productName);
        if (product.isNull()) {
            return purchaseReportFactory.createProductNotFound(userName, productName);
        }
        User user = this.userRepository.find(userName);
        if (user.isNull()) {
        	return purchaseReportFactory.createNotRegistered(userName);
        }
        try {
        	return user.purchase(product);
        } catch(Exception ex) {
        	ex.printStackTrace();
        	return purchaseReportFactory.createFailedPurchase();
        }
    }
}
