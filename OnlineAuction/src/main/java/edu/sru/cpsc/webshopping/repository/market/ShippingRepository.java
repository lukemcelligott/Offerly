package edu.sru.cpsc.webshopping.repository.market;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.billing.PaymentDetails;
import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress;
import edu.sru.cpsc.webshopping.domain.market.Shipping;
import edu.sru.cpsc.webshopping.domain.user.User;

public interface ShippingRepository extends CrudRepository<Shipping, Long> {
	Shipping findByAddress(ShippingAddress address);
}
