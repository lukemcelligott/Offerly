package edu.sru.cpsc.webshopping.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.repository.billing.ShippingAddressRepository;

@WebMvcTest(ShippingAddressDomainController.class)
public class ShippingAddressDomainControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private ShippingAddressDomainController shippingController;
	@Mock
	ShippingAddressRepository shippingAddressRepository;
	
	private ShippingAddress address;

	
	@Test
	public void getShippingAddressTest() throws Exception
	{
		when(shippingAddressRepository.findById(54L).orElseThrow(() 
					-> new IllegalArgumentException("Invalid ID passed to getShippingEntry"))).thenReturn(address);
		this.mvc.perform(get("/get-shipping-address-entry/54")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().string(containsString("Hello, Mock")));
	}
}
