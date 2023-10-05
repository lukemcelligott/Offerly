package edu.sru.cpsc.webshopping.domain.market;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.billing.StateDetails;

@Entity
public class PickupAddress {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	private String streetAddress;
	
	@NonNull
	private String extraLocationInfo;
	
	@NonNull
	private String postalCode;
	
	@NonNull
	private String city;
	
	@NonNull
	@OneToOne
	private StateDetails state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getExtraLocationInfo() {
		return extraLocationInfo;
	}

	public void setExtraLocationInfo(String extraLocationInfo) {
		this.extraLocationInfo = extraLocationInfo;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public StateDetails getState() {
		return state;
	}

	public void setState(StateDetails state) {
		this.state = state;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
