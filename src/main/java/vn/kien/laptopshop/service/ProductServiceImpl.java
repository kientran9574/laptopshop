package vn.kien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kien.laptopshop.model.Product;
import vn.kien.laptopshop.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteProduct(long id) {
        // TODO Auto-generated method stub
        this.productRepository.deleteById(id);
        
    }

    @Override
    public Optional<Product> findById(long id) {
        // TODO Auto-generated method stub
        return this.productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProduct() {
        // TODO Auto-generated method stub
        return this.productRepository.findAll();
    }

    @Override
    public Product getByName(String name) {
        // TODO Auto-generated method stub
        return new Product();
    }

    @Override
    public Product saveProduct(Product product) {
        // TODO Auto-generated method stub
        return this.productRepository.save(product);
    }
    
}
