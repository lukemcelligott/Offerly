package edu.sru.cpsc.webshopping.repository.market;

import org.springframework.data.repository.CrudRepository;
import edu.sru.cpsc.webshopping.domain.market.Bid;

public interface BidRepository extends CrudRepository<Bid, Long> {
    // You can add custom query methods here if needed in the future
}
