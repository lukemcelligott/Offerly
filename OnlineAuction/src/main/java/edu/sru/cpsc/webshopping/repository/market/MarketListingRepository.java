package edu.sru.cpsc.webshopping.repository.market;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;


public interface MarketListingRepository extends CrudRepository<MarketListing, Long> {
	List<MarketListing> findBySeller(User user);
	MarketListing findByWidgetSold(Widget widget);
}
