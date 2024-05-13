package vn.kien.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.kien.laptopshop.service.CustomUserDetailsServices;
import vn.kien.laptopshop.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // thực chất đang ghi đè lại của userdetails của spring security config
    // khi spring dùng cơ chế dependency injection m tim luôn cái UserService
    // userService
    public UserDetailsService userDetailsService(UserService userService) {
        // việc return lại cái class CustomUserDetailsServices trả ra cái này
        // do trả ra cái kiểu dữ liệu UserDetailsService thằng spring sẽ ghi đè lại
        // chính nó
        // ==> ghi đè lại UserDetailsService bằng chính class này của chúng ta
        // truyền vào userService do chúng ta khai báo cái hàm tạo này
        return new CustomUserDetailsServices(userService);
    }

    // @Bean
    // // AuthenticationManager có tác dụng sau chuỗi các sự kiện lại với nhau cho
    // các bạn
    // // nó hoạt động như thế nào trong hệ thống của chúng ta
    // // khai báo userDetailsService(userDetailsService) vào
    // public AuthenticationManager authenticationManager(HttpSecurity http,
    // PasswordEncoder passwordEncoder,
    // UserDetailsService userDetailsService) throws Exception {
    // AuthenticationManagerBuilder authenticationManagerBuilder = http
    // // trả ra tên class nó yêu cầu cái thành phần đấy
    // .getSharedObject(AuthenticationManagerBuilder.class);
    // authenticationManagerBuilder
    // .userDetailsService(userDetailsService)
    // .passwordEncoder(passwordEncoder);
    // return authenticationManagerBuilder.build();
    // // ==> nạp tất cả các thông tin mà chúng ta custom vào
    // }

    @Bean
    // ghi đè Dao
    // fix được vòng lặp vô hạn
    public DaoAuthenticationProvider authProvider(
            // 2 cái function này
            // nói với nó rằng sử dụng cái PasswordEncoder của tao , UserDetailsService của
            // tao này
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // nạp nó luôn , m sử dụng hàm của tao này
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        // cấu hình cái lỗi mà chúng ta muốn hiển thị , tắt đi cái thông báo lỗi mặc
        // định của spring security
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }
    
    public AuthenticationSuccessHandler customSuccessHandler() {
        // class custom của chúng ta
        return new CustomSuccessHandler();
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    // dòng số 1 bất cứ request nào gửi lên đều cho vào hết , không phân biệt người
    // dùng
    // đoạn code thứ hai á nói với java spring á bây giờ login của t á mày phải vào
    // cái đường url này cho t , trong trường hợp t retruen false thì m vào cái dòng
    // ở dưới , ai cũng có quyền vào trang login này
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                DispatcherType.INCLUDE)
                        .permitAll()
                        // đây sẽ là đường link mà cho phép tất cả người dùng truy cập
                        .requestMatchers("/", "/login", "/product/**", "/client/**", "/css/**", "/js/**",
                                "/images/**")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                //  quản lý session 
                .sessionManagement((sessionManagement) -> sessionManagement
                // luôn tạo session mới , nếu như ng dùng chưa có session chúng ta sẽ tạo mới
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        // trong trường hợp mà chúng ta hết hạn session sẽ tự động logout các bạn ra
                        .invalidSessionUrl("/logout?expired")
                        // tại một thời điểm có bao nhiêu tài khoản đăng nhập  , giới hạn 1 tài khoản đăng nhập tối đa trên bao nhiêu thiết bị
                        .maximumSessions(1)
                        // nếu như có 2 người đăng nhạp vào , người thứ hai sẽ đá người trước ra
                        .maxSessionsPreventsLogin(false))
                        // mỗi lần các bạn đăng xuất ra sẽ xóa đi cái cookie này , đồng thời báo cho serve biết là cái session hết hạn
                .logout(logout->logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))
                // ---------------------------------------------------------
                .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .successHandler(customSuccessHandler())
                        .permitAll())
                // nói với spring bây giờ phần access deny thì m phải chạy vào đường url này
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));
        return http.build();
    }
}
