package vn.kien.laptopshop.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServices implements UserDetailsService {
    private UserService userService;

    @Autowired
    public CustomUserDetailsServices(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        vn.kien.laptopshop.model.User user = this.userService.findByEmailUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not login");
        }
        // cần phải trả ra UserDetails để cho spring security quản lý
        // và import cái đối tượng của security tại vì security sẽ chỉ nhận UserDetails
        // thôi (tức là cái đối tượng của nó )
        // User này đến từ security
        return new User(
                user.getEmail(),
                user.getPasword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())));
    }

}
