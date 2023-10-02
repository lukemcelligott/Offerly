package edu.sru.cpsc.webshopping.controller.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shippo.model.Track;

import edu.sru.cpsc.webshopping.controller.TransactionController;
import edu.sru.cpsc.webshopping.domain.market.Shipping;
import edu.sru.cpsc.webshopping.service.shipping.ShippoTrackingService;
import edu.sru.cpsc.webshopping.service.shipping.TrackDTO;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {
    @Autowired
    private ShippoTrackingService shippoTrackingService;
    @Autowired
    private TransactionController transController;
    
    @GetMapping("/{transId}")
	@ResponseBody
	public TrackDTO getTrackingInformation(@PathVariable("transId") long transId) {
		Shipping order = transController.getTransaction(transId).getShippingEntry();
		if (order != null) {
			return shippoTrackingService.getTrackingInformation(order.getCarrier(), order.getTrackingNumber());
		} else {
			throw new IllegalArgumentException("Invalid order ID passed to getTrackingInformation");
		}
	}
}
