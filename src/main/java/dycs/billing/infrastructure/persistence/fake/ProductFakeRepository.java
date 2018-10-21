package dycs.billing.infrastructure.persistence.fake;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dycs.ApplicationContextHolder;
import dycs.billing.domain.entity.Product;
import dycs.billing.domain.entity.ProductNull;
import dycs.billing.domain.entity.PurchaseReportFactory;
import dycs.billing.domain.repository.ProductRepository;
import dycs.common.domain.valueobject.Money;

@Repository
public class ProductFakeRepository implements ProductRepository {
	@Autowired
	PurchaseReportFactory purchaseReportFactory;
	
    public List<Product> getAll() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Product 1", new Money(15, "USD")));
        products.add(new Product("Product 2", new Money(20, "USD")));
        products.add(new Product("Product 3", new Money(25, "USD")));
        return products;
    }
    
    public Product find(String name) {
        List<Product> products = this.getAll();
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return ApplicationContextHolder.getContext().getBean(ProductNull.class);
    }
}
