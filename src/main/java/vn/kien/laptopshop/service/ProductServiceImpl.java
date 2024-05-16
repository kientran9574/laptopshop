package vn.kien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.kien.laptopshop.model.Cart;
import vn.kien.laptopshop.model.CartDetail;
import vn.kien.laptopshop.model.Order;
import vn.kien.laptopshop.model.OrderDetails;
import vn.kien.laptopshop.model.Product;
import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.repository.CartDetailRepository;
import vn.kien.laptopshop.repository.CartRepository;
import vn.kien.laptopshop.repository.OrderDetailRepository;
import vn.kien.laptopshop.repository.OrderRepository;
import vn.kien.laptopshop.repository.ProductRepository;
import vn.kien.laptopshop.repository.UserRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    private CartDetailRepository cartDetailRepository;
    private CartRepository cartRepository;
    private UserService userService;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CartDetailRepository cartDetailRepository,
            CartRepository cartRepository, UserService userService, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
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

    @Override
    public void handlerAddProductToCart(String email, long productId, HttpSession session , long quantity) {

        // lấy lên đối tượng người dùng
        // truyen vao email dang nhap cua nguoi dung tuc la (username)
        // luu cart_details

        User user = this.userService.getByEmail(email);
        if (user != null) {
            // check user da co cart hay chua ? neu chua -> tao moi
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // tao moi cart
                Cart newCart = new Cart();
                // set cai cart nay thuoc ve user nao , chinh la user ma ban dang nhap
                newCart.setUser(user);
                // boi vi ban dau la 0 , sau khi tao thanh cong cai cart_Details tang so luong
                // no len
                newCart.setSum(0);
                // chua co luu cart nay
                // neu nhu chung ta chua tung co 1 gio hang , cai gio hang o tren no se tao moi
                // va cai ham duoi sau khi ma no luu no se tra ve bien cart
                cart = this.cartRepository.save(newCart);
            }
            // trong truong hop ma chung ta da tung co gio hang roi khong can thuc hien buoc
            // tren nua
            // -------------------------------------------------------------------------------------
            // luu cart_detail
            // tao ra doi tuong va moi lan chung ta nhan them vao gio hang chung ta chi co
            // duy nhat 1 doi tuong thoi , them 1 san pham 1 lan
            // tim productById
            // do co phan Optional check xem cai bien nay co ton tai hay khong , co thanh
            // null hay khong
            Optional<Product> product = this.productRepository.findById(productId);
            if (product.isPresent()) {
                // lay ra doi tuong
                Product realProduct = product.get();

                // check san pham da tung them vao gio hang truoc day chua?
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
                // CHECK xem thang nay da ton tai trong database chua
                if (oldDetail == null) {
                    // ----
                    CartDetail newCartDetail = new CartDetail();
                    // set cac thuoc tinh
                    // set phan CartDetail nay no thuoc cai gio hang nao
                    // day chinh la logic giup chung ta luu thang cart_details va luu 2 table cung 1
                    // luc
                    newCartDetail.setCart(cart);
                    newCartDetail.setProduct(realProduct);
                    newCartDetail.setPrice(realProduct.getPrice());
                    newCartDetail.setQuantity(quantity);
                    this.cartDetailRepository.save(newCartDetail);

                    // update cart (sum) , chi cap nhat o day thoi
                    int s = cart.getSum() + 1;
                    // trong truong hop da ton tai san pham day roi , thi gio hang cua chung ta
                    // khong tang len nua
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    // xac xuat co the bi sai vi khi upload database sau do moi goi toi no
                    // đảm bảo chắc ăn rằng là chúng ta gán vào gì chúng ta lưu vào session là vậy
                    session.setAttribute("sum", s);

                } else {
                    // trong truong hop no da ton tai roi
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }
    }

    @Override
    // tìm giỏ hàng theo người dùng
    public Cart fetcherByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    @Override
    public void handleRemoveCartDetail(long cartDetailsId, HttpSession session) {
        // logic giúp xóa sản phẩm ra khỏi giỏ hàng và cập nhật luôn giá trị của giỏ
        // hàng
        // Tìm cart-detail theo id
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailsId);
        if (cartDetailOptional.isPresent()) {
            // nếu mà có tồn tại thì lấy ra
            CartDetail cartDetail = cartDetailOptional.get();
            // Từ cart-detail này, lấy ra đối tượng cart (giỏ hàng) tương ứng
            Cart currentCart = cartDetail.getCart();
            // Xóa cart-detail
            this.cartDetailRepository.deleteById(cartDetailsId);
            // cập nhật luôn giá trị của giỏ hàng
            if (currentCart.getSum() > 1) {
                // Nếu Cart có sum > 1 => update trừ đi 1 đơn vị (update cart)
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                // cập nhật luôn thằng session
                session.setAttribute("sum", s);
                // luu cart
                this.cartRepository.save(currentCart);
            } else {
                // Nếu Cart có sum = 1 => xóa cart
                this.cartRepository.deleteById(currentCart.getId());
                // cập nhật luôn thằng session về 0
                session.setAttribute("sum", 0);
            }
        }

    }

    @Override
    public void handleUpdateBeforeCheckOut(List<CartDetail> cartDetails) {
        // logic
        for (CartDetail cartDetail : cartDetails) {
            // check dk
            Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cartDetailOptional.isPresent()) {
                // lay ra va cap nhat so luong hien tai
                CartDetail currentCartDetail = cartDetailOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }

    }

    @Override
    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {
        // mình chưa vội tạo cái đơn order
        // cần tính ra số tiền trước

        // lấy ra giỏ hàng
        // step 1 : get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {

            // lay ra chi tiet gio hang
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                // logic
                // mình chưa vội tạo cái order, mình cần tính ra số tiền
                // create order
                // nhờ việc lấy ra được chi tiết giỏ hàng ,
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");
                order.setReceiverAddress(receiverAddress);
                double sum = 0;
                for (CartDetail cartDetail : cartDetails) {
                    // chúng ta giờ sẽ tính toán tổng giá trị của đơn hàng này
                    sum += cartDetail.getPrice();
                }
                // sau đó sẽ set trường giá trị
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);
                // sau khi luu order xuống db có thể lấy ra được Id của nó
                for (CartDetail cartDetail : cartDetails) {
                    // tao xong order_detail va ung voi cart_detail se tao phan order_Details tuong
                    // ung
                    // create order_detail
                    OrderDetails od = new OrderDetails();
                    // gan tung gia tri cho no
                    // da co id cua order roi
                    od.setOrder(order);
                    // lay ra product id
                    od.setProduct(cartDetail.getProduct());
                    od.setPrice(cartDetail.getPrice());
                    od.setQuantity(cartDetail.getQuantity());
                    this.orderDetailRepository.save(od);
                }
                // step 2 : delete cart_detail and cart
                for (CartDetail cartDetail : cartDetails) {
                    this.cartDetailRepository.deleteById(cartDetail.getId());
                }
                // xoa thang cart
                this.cartRepository.deleteById(cart.getId());
                // step 3 update session
                session.setAttribute("sum", 0);
            }
        }

    }


}
