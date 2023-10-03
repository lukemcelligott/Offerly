package edu.sru.cpsc.webshopping.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.Bid;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.market.AuctionRepository;
import edu.sru.cpsc.webshopping.repository.market.BidRepository;

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
    
    public Set<User> findUniqueBiddersForListing(MarketListing marketListing) {
        List<Bid> bids = bidRepository.findByAuction(marketListing.getAuction());
        
        Set<User> uniqueBidders = new HashSet<>();
        for (Bid bid : bids) {
            uniqueBidders.add(bid.getBidder());
        }
        return uniqueBidders;
    }

    public int countUniqueBiddersForListing(MarketListing marketListing) {
        return findUniqueBiddersForListing(marketListing).size();
    }
    
    public int getTotalBidsForListing(MarketListing marketListing) {
        return bidRepository.findByAuction(marketListing.getAuction()).size();
    }
    
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    
    }
    
    public Bid findHighestBidForAuction(Auction auction) {

        List<Bid> bids = bidRepository.findByAuctionOrderByBidAmountDesc(auction);     
        
        if (bids.isEmpty()) {
            System.out.println("No bids found for the auction with ID: " + auction.getId());
            return null;
        } else {
            Bid highestBid = bids.get(0);
            System.out.println("The highest bid for the auction with ID " + auction.getId() + " is: " + highestBid.getBidAmount() + " by user: " + highestBid.getBidder().getUsername());
            return highestBid;
        }
    }
    
}
