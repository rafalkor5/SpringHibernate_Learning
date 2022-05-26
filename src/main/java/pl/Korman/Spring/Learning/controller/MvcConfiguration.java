package pl.Korman.Spring.Learning.controller;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

//To jest potrzebne do działa interceptora
@RequiredArgsConstructor //Konstruktor
@Configuration
class MvcConfiguration implements WebMvcConfigurer {
    @NonNull
    private Set<HandlerInterceptor> interceptors;
    // Zbiór naszych wszystkich zaimplementowanych interceptorów
    // za pomocą konstruktora spring wszystkie znalezione wstrzykuje

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor); // metoda wykonana na Secie interceptors
    }

}
