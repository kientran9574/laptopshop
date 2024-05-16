package vn.kien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.kien.laptopshop.model.Cart;
import vn.kien.laptopshop.model.CartDetail;
import vn.kien.laptopshop.model.User;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);

}
