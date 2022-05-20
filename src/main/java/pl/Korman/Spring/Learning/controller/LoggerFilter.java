package pl.Korman.Spring.Learning.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


//FIltrowanie zapytań post http
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class LoggerFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger((LoggerFilter.class));
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest){
            var httprequest = (HttpServletRequest) request;
            //jeżeli wystąpiło zapytanie zapisz je do loggera
            logger.info("[dofilter]" + httprequest.getMethod() + " " + httprequest.getRequestURI());
        }
        chain.doFilter(request,response);  //zapytanie są przesyłane dalej do springa po filtrowaniu
       // logger.info("[postFilter]");
    }


}
