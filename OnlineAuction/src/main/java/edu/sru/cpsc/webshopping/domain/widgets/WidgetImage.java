package edu.sru.cpsc.webshopping.domain.widgets;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


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
