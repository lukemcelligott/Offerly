package edu.sru.cpsc.webshopping.domain.widgets;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;

public class WidgetImageDomainTest {
	@Test
	
	/*
	 * Tests the WidgetImage domain
	 */
	void WidgetImageTest()
	{
		MarketListing ML = new MarketListing();
		ML.setId(12);
		WidgetImage WI = new WidgetImage();
		WI.setImageName("test.png");
		WI.setId(11L);
		WI.setMarketListing(ML);
		
		assertEquals("test.png", WI.getImageName());
		assertEquals(new Long(11), WI.getId());
		assertEquals(ML, WI.getMarketListing());
	}
	
}
