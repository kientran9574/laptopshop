package vn.kien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kien.laptopshop.model.Order;
import vn.kien.laptopshop.model.OrderDetails;
import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.repository.OrderDetailRepository;
import vn.kien.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> getAll() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> findById(long id) {
        return this.orderRepository.findById(id);
    }

    public Order updateOrder(Order order) {
        Optional<Order> optional = this.orderRepository.findById(order.getId());
        if (optional.isPresent()) {
            Order currentOrder = optional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }

        return this.orderRepository.saveAndFlush(order);
    }

    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void deleteOrder(long id) {
        // tại vì lỗi là do ràng buộc dữ liệu , bát buộc phải xóa order_details trước
        // rồi mới tới order
        Optional<Order> optional = this.fetchOrderById(id);
        if (optional.isPresent()) {
            Order order = optional.get();
            List<OrderDetails> orderDetails = order.getOrderDetails();
            for (OrderDetails orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }
        this.orderRepository.deleteById(id);
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
}
}
