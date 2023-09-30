package edu.sru.cpsc.webshopping.repository.widgets;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;

public interface WidgetImageRepository extends CrudRepository<WidgetImage, Long>{

	List<WidgetImage> findByMarketListing(MarketListing marketListing);
}
