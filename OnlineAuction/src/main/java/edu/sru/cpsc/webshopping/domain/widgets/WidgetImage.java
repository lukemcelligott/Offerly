package edu.sru.cpsc.webshopping.domain.widgets;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;


@Entity
public class WidgetImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	private String imageName;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private MarketListing marketListing;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public MarketListing getMarketListing() {
		return marketListing;
	}

	public void setMarketListing(MarketListing marketListing) {
		this.marketListing = marketListing;
	}



}
