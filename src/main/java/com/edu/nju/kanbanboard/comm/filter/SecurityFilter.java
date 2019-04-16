package com.edu.nju.kanbanboard.comm.filter;


import com.edu.nju.kanbanboard.comm.Const;
import com.edu.nju.kanbanboard.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SecurityFilter implements Filter {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Set<String> GreenUrlSet = new HashSet<>();

    @Autowired
    private UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        GreenUrlSet.add("/login");
        GreenUrlSet.add("/register");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if(request.getSession().getAttribute(Const.LOGIN_SESSION_KEY) == null){
            if(GreenUrlSet.contains(uri)){
                logger.debug("don't check url,"+request.getRequestURI());
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }else{
                logger.debug("security filter,deny "+request.getRequestURI());
                String html = "<script type=\"text/javascript\">window.location.href=\"_BP_login\"</script>";
                html = html.replace("_BP_",Const.BASE_PATH);
                servletResponse.getWriter().write(html);
            }
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }


}
