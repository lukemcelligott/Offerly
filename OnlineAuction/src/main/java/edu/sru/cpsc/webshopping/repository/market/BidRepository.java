package edu.sru.cpsc.webshopping.repository.market;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.Bid;
import java.util.List;

public interface BidRepository extends CrudRepository<Bid, Long> {
	List<Bid> findByAuction(Auction auction);
	
	List<Bid> findByAuctionOrderByBidAmountDesc(Auction auction);
    // You can add custom query methods here if needed in the future
}
