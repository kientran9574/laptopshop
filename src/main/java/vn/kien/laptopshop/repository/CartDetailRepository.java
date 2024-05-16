package vn.kien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.kien.laptopshop.model.Cart;
import vn.kien.laptopshop.model.CartDetail;
import vn.kien.laptopshop.model.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    boolean existsByCartAndProduct(Cart cart , Product product);

    CartDetail findByCartAndProduct(Cart cart , Product product);
}
