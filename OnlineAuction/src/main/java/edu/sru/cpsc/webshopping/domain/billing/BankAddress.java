package edu.sru.cpsc.webshopping.domain.billing;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;

@Entity
public class BankAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	private String bankName;
	
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

		// Sets the non-id fields of the calling PaymentDetails to match that of the passed PaymentDetails
		public void transferFields(BankAddress other) {
			this.state = other.state;
			this.postalCode = other.postalCode;
			this.streetAddress = other.streetAddress;
			this.extraLocationInfo = other.extraLocationInfo;
			this.bankName = other.bankName;
			this.city = other.city;
		}

	public boolean buildFromForm(BankAddress_Form other) {
		this.bankName = other.getBankName();
		this.streetAddress = other.getStreetAddress();
		this.extraLocationInfo = other.getExtraLocationInfo();
		this.postalCode = other.getPostalCode();
		this.state = other.getState();
		this.city = other.getCity();
		return true;
	}
}