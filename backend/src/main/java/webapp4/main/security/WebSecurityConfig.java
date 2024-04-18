package webapp4.main.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }
    /*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity https) throws Exception {
        // Public pages
        https.authorizeRequests(requests -> requests.antMatchers("/").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/login").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/login_error").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/logout").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/chart").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/password").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/register").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/waiting").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/error_404").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/error_403").permitAll());
        https.authorizeRequests(requests -> requests.antMatchers("/error_500").permitAll());

        // Private pages
        https.authorizeRequests(requests -> requests.antMatchers("/profile").hasAnyRole("USER"));
        https.authorizeRequests(requests -> requests.antMatchers("/transfer").hasAnyRole("USER"));
        https.authorizeRequests(requests -> requests.antMatchers("/data").hasAnyRole("USER"));
        https.authorizeRequests(requests -> requests.antMatchers("/loan_request").hasAnyRole("USER"));
        https.authorizeRequests(requests -> requests.antMatchers("/loan_visualizer").hasAnyRole("USER"));
        https.authorizeRequests(requests -> requests.antMatchers("/account").hasAnyRole("ADMIN"));
        https.authorizeRequests(requests ->requests.antMatchers("/transfers_manager").hasAnyRole("ADMIN"));
        https.authorizeRequests(requests -> requests.antMatchers("/profile_manager").hasAnyRole("ADMIN"));


        // Login form
        https.formLogin(login -> login.loginPage("/login"));
        https.formLogin(login -> login.usernameParameter("username"));
        https.formLogin(login -> login.passwordParameter("password"));
        https.formLogin(login -> login.defaultSuccessUrl("/profile_forward"));
        https.formLogin(login -> login.failureUrl("/login_error"));

        // Logout
        https.logout(logout -> logout.logoutUrl("/logout"));
        https.logout(logout -> logout.logoutSuccessUrl("/"));

        // Disable csrf
        https.csrf(csrf -> csrf.disable());
    }
}
