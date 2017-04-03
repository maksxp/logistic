package ua.viasat.logistic.configuration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority -> {
            if(authority.getAuthority().equals("ADMIN")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/admin/allUsers");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if(authority.getAuthority().equals("VIASAT")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/viasat/allOrdersList");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(authority.getAuthority().equals("WAREHOUSE")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/Warehouse/warehouseOrdersList");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            else if(authority.getAuthority().equals("DTH")) {
                try {
                    redirectStrategy.sendRedirect(request, response, "/DTH/ordersList");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                throw new IllegalStateException();
            }
        });

    }

}