package vn.kien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import vn.kien.laptopshop.model.Product;

public interface ProductService {
    List<Product> getAllProduct();

    Optional<Product> findById(long id);

    Product saveProduct(Product user);

    void deleteProduct(long id);

    Product getByName(String name);
}
