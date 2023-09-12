package edu.sru.cpsc.webshopping.secure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import edu.sru.cpsc.webshopping.controller.StatisticsDomainController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;

public class WebshoppingSecureSpringTest {
	
	public WebshoppingSecureSpringTest(UserRepository userRepository, UserController userController,StatisticsDomainController statControl) {}
	    @TestConfiguration
	    static class UserDetailsServiceImplTestContextConfiguration {
	 
	        @Bean
	        public UserDetailsService userDetailsService() {
	            return new UserDetailsServiceImpl(null, null, null);
	        }
	    }
	
	@MockBean
	private UserRepository userRepo;
	
	@Autowired
	UserDetailsServiceImpl impl;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(impl).isNotNull();
	}
	
	@Test
	void UserDetailsServiceImplTest() {
		
		UserDetailsServiceImpl impl = new UserDetailsServiceImpl(null, null, null);
		UserDetails user = impl.loadUserByUsername("userName");
		
		assertEquals("userName", user.getUsername());
	}
	
	@Test
	void UserDetailsServiceImplExceptionTest() {
		UserDetailsServiceImpl impl = new UserDetailsServiceImpl(null, null, null);
		
		try {
		UserDetails user = impl.loadUserByUsername("thisUserDoesntExist");
		
		assertEquals("thisUserDoesntExist", user.getUsername());
		}
		catch(Exception e)
		{
			assertTrue(true);
		}
	}
}
