package vn.kien.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.kien.laptopshop.model.Product;
import vn.kien.laptopshop.service.ProductServiceImpl;
import vn.kien.laptopshop.service.UploadFileService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    private ProductServiceImpl productServiceImpl;
    private UploadFileService uploadFileService;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl, UploadFileService uploadFileService) {
        this.productServiceImpl = productServiceImpl;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/admin/product")
    private String getProduct(Model model) {
        List<Product> products = this.productServiceImpl.getAllProduct();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    private String getProductCreate(Model model) {
        model.addAttribute("newsProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String postMethodName(@ModelAttribute("newsProduct") @Valid Product product,
            BindingResult bindingResult,
            Model model,
            @RequestParam("hoidanitFile") MultipartFile file) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError error : fieldErrors) {
            System.out.println(">>>>>>" + error.getField() + "-" + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }
        String image = this.uploadFileService.handleUploadFile(file, "product");
        product.setImage(image);
        Product newsProduct = this.productServiceImpl.saveProduct(product);
        model.addAttribute("newsProduct", newsProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getMethodName(@PathVariable long id, Model model) {
        Product product = this.productServiceImpl.findById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "admin/product/details";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateMethodName(@PathVariable long id, Model model) {
        Product product = this.productServiceImpl.findById(id).get();
        model.addAttribute("newsProducts", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateMethodName(@ModelAttribute("newsProducts") @Valid Product product, BindingResult bindingResult,
            Model model, @RequestParam("hoidanitFile") MultipartFile file) {

        List<FieldError> errors = bindingResult.getFieldErrors();
        for(FieldError error : errors){
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Product updateProduct = this.productServiceImpl.findById(product.getId()).get();
        if (updateProduct != null) {
            if (!file.isEmpty()) {
                String image = this.uploadFileService.handleUploadFile(file, "product");
                updateProduct.setImage(image);
            }
        }
        updateProduct.setName(product.getName());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setQuantity(product.getQuantity());
        updateProduct.setDetailDesc(product.getDetailDesc());
        updateProduct.setShortDesc(product.getShortDesc());
        updateProduct.setFactory(product.getFactory());
        updateProduct.setTarget(product.getTarget());
        productServiceImpl.saveProduct(updateProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteMethodName(@PathVariable long id, Model model) {
        Product deleteProductByID = this.productServiceImpl.findById(id).get();
        model.addAttribute("newsProducts", deleteProductByID);
        model.addAttribute("id", id);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteMethodName(@ModelAttribute("newsProducts") Product product) {
        this.productServiceImpl.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }

}
