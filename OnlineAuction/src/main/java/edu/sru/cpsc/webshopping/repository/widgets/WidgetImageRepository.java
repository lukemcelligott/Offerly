package edu.sru.cpsc.webshopping.repository.widgets;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface WidgetImageRepository extends CrudRepository<WidgetImage, Long>{

	List<WidgetImage> findByMarketListing(MarketListing marketListing);
}
