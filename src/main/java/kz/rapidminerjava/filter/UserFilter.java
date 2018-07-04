package kz.rapidminerjava.filter;

import kz.rapidminerjava.constant.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



public class UserFilter implements Filter {
    public void destroy() {
        //This method is not used
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        HttpSession session = request.getSession();


        if (session.getAttribute(Constant.USER_NAME) == null||
                Constant.EMPTY_STR.equals(session.getAttribute(Constant.USER_NAME) )) { // Checking user session

            response.sendRedirect(request.getContextPath() + "/index.xhtml");

        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        //This method is not used
    }

}
