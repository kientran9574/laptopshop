package vn.hoidanit.laptopshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.learn.Address;
import vn.hoidanit.laptopshop.domain.learn.NguoiDung;
import vn.hoidanit.laptopshop.repository.learn.AddressRepository;
import vn.hoidanit.laptopshop.repository.learn.NguoiDungRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    private final NguoiDungRepository nguoiDungRepository;
    private final AddressRepository addressRepository;

    public UserController(
            UserService userService,
            NguoiDungRepository nguoiDungRepository,
            AddressRepository addressRepository) {
        this.userService = userService;
        this.addressRepository = addressRepository;
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        //
        // List<Address> arrAddress = new ArrayList<>();
        // arrAddress.add(new Address("ha noi"));
        // arrAddress.add(new Address("hcm"));

        // arrAddress.add(new Address("da nang"));

        // this.addressRepository.saveAll(arrAddress);

        Optional<Address> ad1 = this.addressRepository.findById(1L);
        Address ad2 = ad1.get();
        System.out.println("check ad2: " + ad2);

        NguoiDung ng = new NguoiDung(ad2);

        // this.nguoiDungRepository.save(ng);

        List<User> arrUsers = this.userService.getAllUsersByEmail("1@gmail.com");
        System.out.println(arrUsers);

        model.addAttribute("eric", "test");
        model.addAttribute("hoidanit", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
        this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/update/{id}") // GET
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User hoidanit) {
        User currentUser = this.userService.getUserById(hoidanit.getId());
        if (currentUser != null) {
            currentUser.setAddress(hoidanit.getAddress());
            currentUser.setFullName(hoidanit.getFullName());
            currentUser.setPhone(hoidanit.getPhone());

            // bug here
            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User eric) {
        this.userService.deleteAUser(eric.getId());
        return "redirect:/admin/user";
    }
}
