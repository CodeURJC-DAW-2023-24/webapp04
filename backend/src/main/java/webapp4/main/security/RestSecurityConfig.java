package webapp4.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import webapp4.main.security.jwt.JwtRequestFilter;
import webapp4.main.security.jwt.UnauthorizedHandlerJwt;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

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

		https.authenticationProvider(authenticationProvider());
		https.antMatcher("/api/**");
		https.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

		// Public pages
		https.authorizeRequests().antMatchers("/api/login").permitAll();
		https.authorizeRequests().antMatchers("/api/refresh").permitAll();
		https.authorizeRequests().antMatchers("/api/logout").permitAll();
		https.authorizeRequests().antMatchers("/api/accounts").permitAll();

		// Private pages
		https.authorizeRequests().antMatchers("/api/accounts/{id}").hasAnyRole("USER");
		https.authorizeRequests().antMatchers("/api/accounts/{id}/image").hasAnyRole("USER");
		https.authorizeRequests().antMatchers("/api/accounts/{id}/transfers").hasAnyRole("USER");
		https.authorizeRequests().antMatchers("/loan_request").hasAnyRole("USER");
		https.authorizeRequests().antMatchers("/loan_visualizer").hasAnyRole("USER");
		https.authorizeRequests().antMatchers("/api/transfers/{id}").hasAnyRole("ADMIN");
		https.authorizeRequests().antMatchers("/api/transfers").hasAnyRole("ADMIN");
		https.authorizeRequests().antMatchers("/profile_manager").hasAnyRole("ADMIN");
		https.authorizeRequests().antMatchers("/api/loans").hasAnyRole("USER");

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

	}
}
