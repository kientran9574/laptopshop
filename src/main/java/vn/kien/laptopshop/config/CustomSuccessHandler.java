package vn.kien.laptopshop.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.kien.laptopshop.model.User;
import vn.kien.laptopshop.service.UserService;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserService userService;

    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        // {key: value , truy cập đối tượng đấy thông qua key và lấy ra value}
        roleTargetUrlMap.put("ROLE_USER", "/");
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            // so sánh 2 cái key nếu như 2 thằng giống nhau thì sẽ lấy ra authorityName và
            // trả về cái value đó
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }
    protected void clearAuthenticationAttributes(HttpServletRequest request, Authentication authentication) {
        // lời gọi request này nếu như tồn tại một session rồi , sử dụng lại session đấy
        // không tạo mới
        // truyền vào false này ám chỉ rằng là chỉ khi nào có session chúng ta mới lấy
        // ra sử dụng thôi , nếu như không có thì k làm gì cả
        // việc truyền vào false nói với spring biết rằng là t chỉ muốn sd session khi
        // và chỉ khi m có thôi nếu không có thì t sẽ k làm gì cả
        // nếu như không truyền vào cái gì , thì check -> có session hay không ,nếu có
        // thì sẽ sử dụng lại session đấy ,nếu không có sẽ tạo mới
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        String email = authentication.getName();
        User user = this.userService.findByEmailUser(email);
        if (user != null) {
            session.setAttribute("fullName", user.getFullname());
            session.setAttribute("avatar", user.getAvatar());
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // logic login admin , client
        // có trách nhiệm chuyển trang
        // redirect về đâu sau khi login thành công
        // authentication là cái data lưu trữ bên trong spring sercurity
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
        // dọn dẹp phần session khi logout thì xóa hết thông tin
        clearAuthenticationAttributes(request, authentication);
    }
}
