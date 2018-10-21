package dycs.billing.domain.repository;

import java.util.List;

import dycs.billing.domain.entity.Product;

public interface ProductRepository {
    List<Product> getAll();
    Product find(String name);
}
