package vn.kien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import vn.kien.laptopshop.model.Cart;
import vn.kien.laptopshop.model.CartDetail;
import vn.kien.laptopshop.model.Product;
import vn.kien.laptopshop.model.User;

public interface ProductService {
    List<Product> getAllProduct();

    Optional<Product> findById(long id);

    Product saveProduct(Product user);

    void deleteProduct(long id);

    Product getByName(String name);

    void handlerAddProductToCart(String email, long productId, HttpSession session, long quantity);

    Cart fetcherByUser(User user);

    void handleRemoveCartDetail(long cartDetailsId, HttpSession session);

    void handleUpdateBeforeCheckOut(List<CartDetail> cartDetails);

    void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone);

}
