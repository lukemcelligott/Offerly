package edu.sru.cpsc.webshopping.domain.market;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Future;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Auction attributes that will be dynamically changed when users bid
 */

@Entity
public class Auction {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private MarketListing marketListing;

    private BigDecimal startingBid;
    private BigDecimal currentBid;
    private LocalDateTime startAuctionDate;
    
    @Future(message = "End date should be in the future.")
    private LocalDateTime endAuctionDate;
    
    // id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // marketListing
    public MarketListing getMarketListing() {
        return marketListing;
    }

    public void setMarketListing(MarketListing marketListing) {
        this.marketListing = marketListing;
    }

    // startingBid
    public BigDecimal getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(BigDecimal startingBid) {
        this.startingBid = startingBid;
    }

    // currentBid
    public BigDecimal getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(BigDecimal currentBid) {
        this.currentBid = currentBid;
    }

    // startAuctionDate
    public LocalDateTime getStartAuctionDate() {
        return startAuctionDate;
    }

    public void setStartAuctionDate(LocalDateTime startAuctionDate) {
        this.startAuctionDate = startAuctionDate;
    }

    // endAuctionDate
    public LocalDateTime getEndAuctionDate() {
        return endAuctionDate;
    }

    public void setEndAuctionDate(LocalDateTime endAuctionDate) {
        this.endAuctionDate = endAuctionDate;
    }
}
