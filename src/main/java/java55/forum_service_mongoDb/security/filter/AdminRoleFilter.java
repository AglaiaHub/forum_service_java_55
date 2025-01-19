package java55.forum_service_mongoDb.security.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java55.forum_service_mongoDb.accounting.dao.UserAccountRepository;
import java55.forum_service_mongoDb.accounting.dto.exception.UserExistException;
import java55.forum_service_mongoDb.accounting.dto.exception.UserNotFoundException;
import java55.forum_service_mongoDb.accounting.model.Role;
import java55.forum_service_mongoDb.accounting.model.UserAccount;
import java55.forum_service_mongoDb.security.filter.exceptions.NotAdministratorAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Order(20)
public class AdminRoleFilter implements Filter {

    final UserAccountRepository userAccountRepository;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if ((method.equals("PUT") || method.equals("DELETE")) && requestURI.matches("/user/.*/role/.*")) {
            UserAccount userAccount = userAccountRepository.findById(request.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
            Set roles = userAccount.getRoles();
            if (!roles.equals(Role.ADMINISTRATOR)){
                throw new NotAdministratorAccess();
            }
        }

        if ((method.equals("DELETE")) && requestURI.matches("/user/..")) {
            UserAccount userAccount = userAccountRepository.findById(request.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
            Set roles = userAccount.getRoles();
            if (!roles.equals(Role.ADMINISTRATOR)){
                throw new NotAdministratorAccess();
            }
        }

        filterChain.doFilter(request, response);
    }
}

// TODO 1 - найти ошибку.
//  2 - написать методы для администратора
//  3- написать юнит тесты для бизнес логики
