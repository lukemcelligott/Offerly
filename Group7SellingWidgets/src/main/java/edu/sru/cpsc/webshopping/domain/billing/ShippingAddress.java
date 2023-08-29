package edu.sru.cpsc.webshopping.domain.billing;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.user.User;


/**
 * The address to ship a purchase to
 */
@Entity
public class ShippingAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	private String recipient;
	
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
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

		// Sets the non-id fields of the calling PaymentDetails to match that of the passed PaymentDetails
		public void transferFields(ShippingAddress other) {
			this.state = other.state;
			this.postalCode = other.postalCode;
			this.streetAddress = other.streetAddress;
			this.extraLocationInfo = other.extraLocationInfo;
			this.recipient = other.recipient;
			this.city = other.city;
		}

	public boolean buildFromForm(ShippingAddress_Form other) {
		this.recipient = other.getRecipient();
		this.streetAddress = other.getStreetAddress();
		this.extraLocationInfo = other.getExtraLocationInfo();
		this.postalCode = other.getPostalCode();
		this.state = other.getState();
		this.city = other.getCity();
		return true;
	}
}
