package java55.forum_service_mongoDb.security.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java55.forum_service_mongoDb.accounting.dao.UserAccountRepository;
import java55.forum_service_mongoDb.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(20)
public class AdminRoleFilter implements Filter {

    final UserAccountRepository userAccountRepository;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//        chain.doFilter(request, response);
    }
}

// TODO 1 - найти ошибку.
//  2 - написать методы для администратора
//  3- написать юнит тесты для бизнес логики
