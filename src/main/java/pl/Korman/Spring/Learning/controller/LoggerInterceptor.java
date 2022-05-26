package pl.Korman.Spring.Learning.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//FIltrowanie zapytań post http za pomocą Interceptora
// do działania potrzebne MvcConfiguration.class która
// implementuje WebMvcConfigurer oraz metode addintercepter
//@Component off
class LoggerInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger((LoggerFilter.class));

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        logger.info("[dointerceptor]" + request.getMethod() + " " + request.getRequestURI());
        //PreHangler przepuszc za zapytanie dalej gdy zwróci true;
        return true;
    }

    //Przetwarzanier zapytań po użyciu przez springa
//    @Override
//    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
//        logger.info("[postInterceptor]");
//    }
}
