package vn.kien.laptopshop.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.kien.laptopshop.model.Order;
import vn.kien.laptopshop.model.Product;
import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.model.dto.RegisterDTO;
import vn.kien.laptopshop.service.OrderService;
import vn.kien.laptopshop.service.ProductService;
import vn.kien.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;
    private OrderService orderService;

    @Autowired
    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder,
            OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getMethodName(Model model) {
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("products", products);
        return "client/homepage/HomePage";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String postMethodName(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {

        // List<FieldError> errors = bindingResult.getFieldErrors();
        // for (FieldError error : errors) {
        // System.out.println(">>>>" + error.getField() + " - " +
        // error.getDefaultMessage());
        // }
        // validate
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }
        // ------
        // Lay ra data
        User user = this.userService.registerDTOtoUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(user.getPasword());
        user.setPasword(hashPassword);
        user.setRole(this.userService.getByName("USER"));
        this.userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        return "client/auth/login";
    }

    @GetMapping("/access-deny")
    public String getAccess(Model model) {
        return "client/auth/deny";
    }

    @GetMapping("/order-history")
    public String getOrderHistory(Model mode, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        session.setAttribute("orders", orders);
        return "client/cart/history";
    }
}
