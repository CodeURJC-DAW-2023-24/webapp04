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
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity https) throws Exception {
        // Public pages
        https.authorizeRequests().antMatchers("/").permitAll();
        https.authorizeRequests().antMatchers("/login").permitAll();
        https.authorizeRequests().antMatchers("/login_error").permitAll();
        https.authorizeRequests().antMatchers("/logout").permitAll();
        https.authorizeRequests().antMatchers("/chart").permitAll();
        https.authorizeRequests().antMatchers("/password").permitAll();
        https.authorizeRequests().antMatchers("/register").permitAll();
        https.authorizeRequests().antMatchers("/waiting").permitAll();
        https.authorizeRequests().antMatchers("/error_404").permitAll();
        https.authorizeRequests().antMatchers("/error_403").permitAll();
        https.authorizeRequests().antMatchers("/error_500").permitAll();

        // Private pages
        https.authorizeRequests().antMatchers("/profile").hasAnyRole("USER");
        https.authorizeRequests().antMatchers("/transfer").hasAnyRole("USER");
        https.authorizeRequests().antMatchers("/data").hasAnyRole("USER");
        https.authorizeRequests().antMatchers("/loan_request").hasAnyRole("USER");
        https.authorizeRequests().antMatchers("/loan_visualizer").hasAnyRole("USER");
        https.authorizeRequests().antMatchers("/account").hasAnyRole("ADMIN");
        https.authorizeRequests().antMatchers("/transfers_manager").hasAnyRole("ADMIN");
        https.authorizeRequests().antMatchers("/profile_manager").hasAnyRole("ADMIN");


        // Login form
        https.formLogin().loginPage("/login");
        https.formLogin().usernameParameter("username");
        https.formLogin().passwordParameter("password");
        https.formLogin().defaultSuccessUrl("/profile_forward");
        https.formLogin().failureUrl("/login_error");

        // Logout
        https.logout().logoutUrl("/logout");
        https.logout().logoutSuccessUrl("/");

        // Disable csrf
        https.csrf().disable();
    }
}
