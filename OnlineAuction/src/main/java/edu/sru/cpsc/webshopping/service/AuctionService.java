package edu.sru.cpsc.webshopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.repository.market.AuctionRepository;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    /**
     * Creates or updates an auction with the provided details.
     */
    public Auction saveAuction(Auction auction) {
  
        return auctionRepository.save(auction);
    }

}