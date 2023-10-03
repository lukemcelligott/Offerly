package edu.sru.cpsc.webshopping.domain.market;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;

@Entity
public class Pickup {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @OneToOne
    @NonNull
    private MarketListing listing;

    @OneToOne
    @NonNull
    private ShippingAddress location;

    @OneToOne
	private Transaction transaction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ShippingAddress getLocation() {
        return location;
    }

    public void setLocation(ShippingAddress location) {
        this.location = location;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
