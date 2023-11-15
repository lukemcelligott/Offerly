
package edu.sru.cpsc.webshopping.secure;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Resource
	private UserDetailsServiceImpl userDetailsService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		http
			.authorizeHttpRequests(authz -> authz
				/* begin - users visiting these URLs do not need authenticated because of .permitAll() */
				.requestMatchers(
					mvc.pattern("/")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/index")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/newUser")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/adduser")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/add-user-signup")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/emailverification")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/get-all-card-types")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/submitShippingAddressSignUp")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/verify")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/error")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/userSecrets")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/forgotUser/*")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/findUser")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/answerQuestion")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/newUserPayment")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/submitPurchaseSignup")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/resetPassword")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/missionStatement")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/FAQ")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/application")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/apply")
				).permitAll()
				.requestMatchers(
					mvc.pattern("browseWidgets")
				).permitAll()
				.requestMatchers(
					mvc.pattern("/BrowseWidgetsButton")
				).permitAll()
				.requestMatchers(
					antMatcher("/resources/**")
				).permitAll()
				.requestMatchers(
					antMatcher("/static/**")
				).permitAll()
				.requestMatchers(
					antMatcher("/styles/**")
				).permitAll()
				.requestMatchers(
					antMatcher("/js/**")
				).permitAll()
				.requestMatchers(
					antMatcher("/images/**")
				).permitAll()
				.requestMatchers(
					antMatcher("/listingImages/**")
				).permitAll()
				.requestMatchers(
					antMatcher("data:realCaptcha/**")
				).permitAll()

/* 				.requestMatchers(
					"/index",
					"/newUser",
					"/adduser",
					"/add-user-signup",
					"/emailverification",
					"/get-all-card-types",
					"/submitShippingAddressSignUp",
					"/verify",
					"/userSecrets",
					"/forgotUser/*",
					"/findUser",
					"/answerQuestion",
					"/newUserPayment",
					"/submitPurchaseSignup",
					"/resetPassword",
					"/missionStatement",
					"/FAQ",
					"/application",
					"/apply",
					"browseWidgets",
					"/BrowseWidgetsButton",
					"/resources/**",
					"/static/**",
					"/styles/**",
					"/js/**",
					"/images/**",
					"/listingImages/**",
					"data:realCaptcha/**")
					.permitAll() */
				/* end - users visiting these URLs do not need authenticated because of .permitAll() */
				.anyRequest().authenticated() /* outside of permitAll() matches, authenticate any request */
			)
			.authenticationProvider(authenticationProvider())
			.formLogin(form -> form
				.loginPage("/login") /* link to login page */
				.permitAll()
			)
			.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/do_the_logout"))
                .logoutSuccessUrl("/index")
            );
		return http.build();
	}
}