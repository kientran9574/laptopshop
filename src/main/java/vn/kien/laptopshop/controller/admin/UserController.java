package vn.kien.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.service.UploadFileService;
import vn.kien.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class UserController {
    private UserService userService;
    private UploadFileService uploadFileService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, UploadFileService uploadFileService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadFileService = uploadFileService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getMethodName(Model model) {
        // User user = new User();
        List<User> users = this.userService.getByALL();
        model.addAttribute("newsUser", users);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserById(Model model, @PathVariable long id) {
        Optional<User> optionalUser = this.userService.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            model.addAttribute("id", id);
            return "admin/user/details";
        } else {
            // Xử lý khi không tìm thấy user...
            throw new Error("khong tim thay user"); // Ví dụ: Trả về một trang thông báo không tìm thấy user
        }
    }

    @GetMapping("/admin/user/create")
    public String postMethodName(Model model) {
        model.addAttribute("newsUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String postMethodName(@ModelAttribute("newsUser") @Valid User user,
            BindingResult bindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        // Validate
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            // nếu dùng return chỗ này sẽ trả ra giá trị của những cái lỗi nhưng sẽ không
            // trả được file jsp nên mới bị err 404
            System.out.println(">>>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            // render truc tiep ra view va giu lai gia tri cu cua input do
            return "admin/user/create";
        }
        // -----------------------
        String avatar = this.uploadFileService.handleUploadFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(user.getPasword());
        user.setAvatar(avatar);
        user.setPasword(hashPassword);
        user.setRole(this.userService.getByName(user.getRole().getName()));
        this.userService.saveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdate(Model model, @PathVariable long id) {
        Optional<User> user = this.userService.findById(id);
        model.addAttribute("newsUser", user);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@ModelAttribute("newsUser") User user) {
        Optional<User> optionalUser = this.userService.findById(user.getId());
        if (optionalUser.isPresent() && optionalUser != null) {
            User userUpdate = optionalUser.get();
            userUpdate.setAddress(user.getAddress());
            userUpdate.setFullname(user.getFullname());
            userUpdate.setNumber(user.getNumber());
            this.userService.saveUser(userUpdate);
            return "redirect:/admin/user";
        } else {
            // Xử lý khi không tìm thấy user...
            throw new Error("khong tim thay user"); // Ví dụ: Trả về một trang thông báo không tìm thấy user
        }
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDelete(Model model, @PathVariable long id) {
        Optional<User> user = this.userService.findById(id);
        model.addAttribute("newsUser", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String deleteSv(Model model, @ModelAttribute("newsUser") User user) {
        this.userService.deleteSv(user.getId());
        return "redirect:/admin/user";
    }

}
