
package edu.sru.cpsc.webshopping.secure;

import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector).servletPath("/");
		http
			.authorizeHttpRequests(authz -> authz
				/* begin - users visiting these URLs do not need authenticated because of .permitAll() */
				.requestMatchers(
					mvcMatcherBuilder.pattern("/")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/index")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/newUser")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/adduser")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/add-user-signup")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/emailverification")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/get-all-card-types")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/submitShippingAddressSignUp")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/verify")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/userSecrets")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/forgotUser/*")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/findUser")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/answerQuestion")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/newUserPayment")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/submitPurchaseSignup")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/resetPassword")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/missionStatement")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/FAQ")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/application")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/apply")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("browseWidgets")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/BrowseWidgetsButton")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/resources/**")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/static/**")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/styles/**")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/js/**")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/images/**")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("/listingImages/**")
				).permitAll()
				.requestMatchers(
					mvcMatcherBuilder.pattern("data:realCaptcha/**")
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