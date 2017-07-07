package filters;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author kaptan singh
 */


@WebFilter(filterName = "AuthFilter", urlPatterns = {"/vahan/*"})
public class AuthFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    public AuthFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            // check whether session variable is set
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            res.setDateHeader("Expires", 0); // Proxies.
            HttpSession ses = req.getSession(false);
            //  allow user to proccede if url is login.xhtml or user logged in or user is accessing any page in //public folder
            String reqURI = req.getRequestURI();

            if (reqURI.indexOf("/home.xhtml") >= 0 || reqURI.indexOf("/registeration.xhtml") >= 0 ||   reqURI.indexOf("/login.xhtml") >= 0 ||reqURI.indexOf("/weluser.xhtml") >= 0 || (ses != null && ses.getAttribute("USERCD") != null && ses.getAttribute("UserId") != null )
                    || reqURI.indexOf("/public/") >= 0 || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
            } else if (reqURI.equalsIgnoreCase("/NIC/")) {
                res.sendRedirect(req.getContextPath() + "/vahan/home.xhtml");
            } 
           // else {
            //  res.sendRedirect(req.getContextPath() + "/vahan/commonui/login.xhtml");            }
        
         //if(ses==null)
       //  {
        //  res.sendRedirect(req.getContextPath() + "/vahan/home.xhtml");
         //}
            
        } catch (Exception t) {
        
            logger.error("Exception occoured in filter-", t);
        }
    } //doFilter

    @Override
    public void destroy() {
    }

}
