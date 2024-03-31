package webapp4.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import webapp4.main.security.jwt.JwtRequestFilter;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	//Expose AuthenticationManager as a Bean to be used in other services
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity https) throws Exception {


		https.antMatcher("/api/**");
		
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


		// Disable CSRF protection (it is difficult to implement in REST APIs)
		https.csrf().disable();

		// Disable Http Basic Authentication
		https.httpBasic().disable();
		
		// Disable Form login Authentication
		https.formLogin().disable();

		// Avoid creating session 
		https.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Add JWT Token filter
		https.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		//swagger sec
		https.authorizeRequests(requests -> requests
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated())
                .formLogin(login -> login.permitAll())
                .logout(logout -> logout.permitAll());
	}
}
