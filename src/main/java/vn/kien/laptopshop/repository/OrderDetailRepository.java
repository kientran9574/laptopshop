package vn.kien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.kien.laptopshop.model.OrderDetails;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {

}
