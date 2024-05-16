package vn.kien.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.kien.laptopshop.model.Order;
import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.service.OrderService;
import vn.kien.laptopshop.service.UserService;
import vn.kien.laptopshop.service.UserServiceImpl;

@Controller
public class DashBoardController {

    private final UserServiceImpl userServiceImpl;
    private final OrderService orderServicell;

    public DashBoardController(UserServiceImpl userServiceImpl, OrderService orderServicell) {
        this.userServiceImpl = userServiceImpl;
        this.orderServicell = orderServicell;
    }

    @GetMapping("/admin")
    public String getDashBoard(Model model) {
        model.addAttribute("countUsers", this.userServiceImpl.countUser());
        model.addAttribute("countProducts", this.userServiceImpl.countProduct());
        model.addAttribute("countOrders", this.userServiceImpl.countOrder());
        return "admin/dashboard/show";
    }

}
