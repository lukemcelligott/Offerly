package edu.sru.cpsc.webshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.Bid;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.market.AuctionRepository;
import edu.sru.cpsc.webshopping.repository.market.BidRepository;

import java.math.BigDecimal;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    /**
     * Creates or updates an auction with the provided details.
     *
     */
     
    public Auction saveAuction(Auction auction) {
  
        return auctionRepository.save(auction);
    }
    
    public Bid bid(Auction auction, User user, BigDecimal bidAmount) {
        Bid bid = new Bid(auction, user, bidAmount);
    	return bidRepository.save(bid);
    }

}

