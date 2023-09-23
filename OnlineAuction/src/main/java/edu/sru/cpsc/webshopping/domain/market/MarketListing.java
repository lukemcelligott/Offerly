package edu.sru.cpsc.webshopping.domain.market;

import java.math.BigDecimal;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;

/**
 * Holds information on a marketplace listing that has been posted by a seller,
 * and that a user can buy from 
 */
@Entity
public class MarketListing {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	@Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10, 2)")
	private BigDecimal pricePerItem;
	
	// removing auctionPrice because we are using the Auction class to hold the auction price
/* 	@NonNull
	@Column(precision = 10, scale = 2, columnDefinition="DECIMAL(10, 2)")
	private BigDecimal auctionPrice; */
	
	@NonNull
	@Min(value=0, message="Must have 0 or more items available.")
	private long qtyAvailable;
	
	@NonNull 
	private boolean isDeleted;
	
	@ManyToOne
	@NonNull
	private User seller;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@NonNull
	private Widget widgetSold;
	
	private String coverImage;
	
	@OneToMany(mappedBy = "marketListing", cascade = CascadeType.MERGE)
	private Set<WidgetImage> images;
	
	@OneToMany(mappedBy="marketListing", cascade = CascadeType.MERGE)
	private Set<Transaction> transactions;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "auction_id")
	private Auction auction;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPricePerItem() {
		return pricePerItem;
	}

	public void setPricePerItem(BigDecimal pricePerItem) {
		this.pricePerItem = pricePerItem;
	}
	
	/* public BigDecimal getAuctionPrice() {
		return auctionPrice;
	}

	public void setAuctionPrice(BigDecimal auctionPrice) {
		this.auctionPrice = auctionPrice;
	} */

	public long getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(long qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	
	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	public Widget getWidgetSold() {
		return widgetSold;
	}

	public void setWidgetSold(Widget widgetSold) {
		this.widgetSold = widgetSold;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Set<WidgetImage> getImages() {
		return images;
	}

	public void setImages(Set<WidgetImage> images) {
		this.images = images;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
	
	public Auction getAuction() {
	    return auction;
    }

	public void setAuction(Auction auction) {
	    this.auction = auction;
    }


}
