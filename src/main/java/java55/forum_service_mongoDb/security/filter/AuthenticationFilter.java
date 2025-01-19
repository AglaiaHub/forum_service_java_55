package java55.forum_service_mongoDb.security.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java55.forum_service_mongoDb.accounting.dao.UserAccountRepository;
import java55.forum_service_mongoDb.accounting.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Order(10)
public class AuthenticationFilter implements Filter {

    final UserAccountRepository userAccountRepository;
    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse resp,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        System.out.println("HttpServletRequest: "+request.getHeader("Authorization"));
//        System.out.println("ServletRequest"+req.getServletContext());

        if (checkPoint(request.getMethod(), request.getServletPath())){
            try {
                String[] credentials = getCredentials(request.getHeader("Authorization"));
                UserAccount userAccount = userAccountRepository.findById(credentials[0])
                        .orElseThrow(RuntimeException::new);
                System.out.println(userAccount.getPassword());
                if (!BCrypt.verifyer().verify(credentials[1].toCharArray(), userAccount.getPassword().toCharArray()).verified) {
                    throw new RuntimeException();
                }
                request = new WrappedRequest(request, userAccount.getLogin());
            } catch (Exception e) {
                response.sendError(401);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean checkPoint(String method, String servletPath) {
        return !(HttpMethod.POST.matches(method) && servletPath.matches("/account/register"));

    }

    private String[] getCredentials(String header) {
        String token = header.split(" ")[1];
        String decode = new String((Base64.getDecoder().decode(token)));
        System.out.println(decode);
        return decode.split(":");
    }

    private static class WrappedRequest extends HttpServletRequestWrapper {
        private String login;

        public WrappedRequest(HttpServletRequest request, String login){
            super(request);
            this.login = login;
        }

        @Override
        public Principal getUserPrincipal(){
            return () -> login;
        }
    }
}
