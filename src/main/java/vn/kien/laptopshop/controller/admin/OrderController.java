package vn.kien.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    
    @GetMapping("/admin/order")
    private String getOrder(){
        return "admin/order/show";
    }
}
