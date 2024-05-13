package vn.kien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.kien.laptopshop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
    

