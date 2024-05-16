package vn.kien.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.kien.laptopshop.model.Cart;
import vn.kien.laptopshop.model.CartDetail;
import vn.kien.laptopshop.model.Product;
import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
    private ProductService productService;

    @Autowired
    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getMethodName(Model model, @PathVariable long id) {
        Product product = this.productService.findById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "client/product/details";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String postMethodName(@PathVariable long id, HttpServletRequest request) {
        // lay session nay ra
        HttpSession session = request.getSession(false);
        // điều đầu tiên mình làm lấy ra product Id
        long productId = id;
        // goi toi productService chúng ta sẽ lưu lại
        // goi toi ham nay de xu ly logic
        // truyen vao cai id ( lay ra cai session)
        // lay ra email
        // ep kieu du lieu email , tai getAttribute se tra ve doi tuong
        String email = (String) session.getAttribute("email");
        // truyen thang session qua de co the khi them san pham vao gio hang co the lấy
        // động dữ liệu
        this.productService.handlerAddProductToCart(email, productId, session, 1);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCart(Model model, HttpServletRequest request) {
        // logic lấy giỏ hàng chi tiết
        HttpSession session = request.getSession(false);
        User currentUser = new User();
        // lấy ra user đăng nhập cụ thể trong session
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        // chỗ này sẽ bị lỗi null tại vì khi người dùng vào lần đầu cái giỏ hàng bị
        // không có dữ liệu nên bị null
        Cart cart = this.productService.fetcherByUser(currentUser);
        // lấy ra CartDetail
        // bởi vì nó có mối quan hệ lẫn nhau
        // nên check điều kiện giỏ hàng chi tiết tại vì vào lần đầu thì không có dữ liệu
        // dưới database
        // cho nên sẽ bị null nên để cho nó là 1 mảng rỗng
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        // tính tiền * với số lượng
        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        // mình truyền cái giỏ hàng qua luôn mình k thể tryền cartDetails , bởi vì
        // render view nó k hiểu
        // lấy từ thằng cart ra cart.cartDetails
        // path="cartDetails[${status.index}].quantity" đoạn này là đang láy theo đối
        // tượng chỉ mục của mảng , bắt buộc xuất phát từ đối tượng , không thể xuất
        // phát từ arrays . Vì java spring nó làm việc xuất phát từ đối tượng
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
        // buoc 1 tìm cart_details theo id từ view gửi lên
        HttpSession session = request.getSession(false);
        long cartDetailsId = id;
        // truyen het qua service de xu ly
        this.productService.handleRemoveCartDetail(cartDetailsId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // luc nay user la null
        User currentUser = new User();
        // lay id cua user trong session tuong ung khi nguoi dung dang nhap
        long userId = (long) session.getAttribute("id");
        currentUser.setId(userId);
        // lay ra gio hang tuong ung voi user nào
        Cart cart = this.productService.fetcherByUser(currentUser);
        // người dùng đăng nhập lần đầu vào tránh cho giỏ hàng bị null ép nó về array
        // rỗng
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        // tính tổng giá tiền lấy giá * số lượng
        double totalPrice = 0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String postCheckOut(@ModelAttribute("cart") Cart cart) {
        // lấy ra CartDetail từ view gửi lên bởi vì làm việc với đối tượng nên tương ứng
        // (cart.cartDetails)
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateBeforeCheckOut(cartDetails);
        return "redirect:/checkout";
    }

    // lấy ra tham số theo tên
    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        HttpSession session = request.getSession(false);
        User currentUser = new User();
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThanks(Model model) {
        return "client/cart/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleProductView(@RequestParam("id") long id, @RequestParam("quantity") long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        this.productService.handlerAddProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }

}
