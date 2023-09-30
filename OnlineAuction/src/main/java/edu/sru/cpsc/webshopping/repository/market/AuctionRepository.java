package edu.sru.cpsc.webshopping.repository.market;
import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.Auction;

public interface AuctionRepository extends CrudRepository<Auction, Long> {
    // You can add custom query methods here if needed in the future
}