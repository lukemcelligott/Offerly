package edu.sru.cpsc.webshopping.repository.market;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.AutoBid;
import edu.sru.cpsc.webshopping.domain.user.User;

import java.util.List;

public interface AutoBidRepository extends CrudRepository<AutoBid, Long> {
	List<AutoBid> findByAuction(Auction auction);
//	
//	List<AutoBid> findByAuctionOrderByIdDesc(Auction auction);
//	
//	List<AutoBid> findByAuctionAndUser(Auction auction, User user);
    // You can add custom query methods here if needed in the future
}
