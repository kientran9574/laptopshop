package vn.kien.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.kien.laptopshop.model.Order;
import vn.kien.laptopshop.service.OrderService;


@Controller
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    private String getOrder(Model model) {
        List<Order> orders = this.orderService.getAll();
        model.addAttribute("orders", orders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    private String getOrderDetail(Model model, @PathVariable long id) {
        Order order = this.orderService.findById(id).get();
        model.addAttribute("orders", order);
        model.addAttribute("id", id);
        model.addAttribute("orderDetails", order.getOrderDetails());
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getDeleteOrderPage(@PathVariable long id, Model model) {
        Order order = this.orderService.findById(id).get();
        model.addAttribute("id", id);
        model.addAttribute("newOrder", order);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String deleteOrderPage(@ModelAttribute("newOrder") Order order) {
        this.orderService.deleteOrder(order.getId());
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/update/{id}")
    public String updateOrder(@PathVariable long id, Model model) {
        Optional<Order> orOptional = this.orderService.findById(id);
        model.addAttribute("newOrder", orOptional.get());
        return "admin/order/update";
    }
    @PostMapping("/admin/order/update")
    public String postUpdate(@ModelAttribute("newOrder") Order order) {
        this.orderService.updateOrder(order);
        return "redirect:/admin/order";
    }
    
}
