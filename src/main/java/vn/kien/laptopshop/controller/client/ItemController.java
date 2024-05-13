package vn.kien.laptopshop.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.kien.laptopshop.model.Product;
import vn.kien.laptopshop.service.ProductService;

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

}
