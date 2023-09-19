
package edu.sru.cpsc.webshopping.secure;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.WebApplicationContext;




@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Resource
	private UserDetailsServiceImpl userDetailsService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
	
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		
			.authorizeRequests()
				/* begin - users visiting these URLs do not need authenticated because of .permitAll() */
				.antMatchers("/", "/index", "/newUser", "/adduser", "/add-user-signup","/emailverification", "/get-all-card-types", "/submitShippingAddressSignUp",
						"/verify", "/userSecrets", "/forgotUser/*","/findUser","/answerQuestion", "/newUserPayment", "/submitPurchaseSignup",
						"/resetPassword","/missionStatement","/FAQ","/application","/apply", "browseWidgets","/BrowseWidgetsButton").permitAll()
				.antMatchers("/resources/**", "/static/**", "/styles/**", "/js/**", "/images/**","/listingImages/**","data:realCaptcha/**").permitAll()
				/* end - users visiting these URLs do not need authenticated because of .permitAll() */
				.anyRequest().authenticated() /* outside of permitAll() matches, authenticate any request */
				.and()
			.formLogin()
				.loginPage("/login") /* link to login page */
				.permitAll()
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/do_the_logout")).logoutSuccessUrl("/index"); /* redirect to index after logging out */
	}
    /*
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = 
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
	*/
}