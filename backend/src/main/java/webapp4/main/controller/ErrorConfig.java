package webapp4.main.controller;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ErrorConfig implements ErrorPageRegistrar, WebMvcConfigurer {
    @Override
    public void addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {
        registry.addViewController("/error_404").setViewName("error_404");
        registry.addViewController("/error_403").setViewName("error_403");
        registry.addViewController("/error_500").setViewName("error_500");
    }

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error_404"),
                new ErrorPage(HttpStatus.FORBIDDEN, "/error_403"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error_500")
        );
    }
}
