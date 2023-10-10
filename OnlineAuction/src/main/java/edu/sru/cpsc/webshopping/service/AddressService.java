package edu.sru.cpsc.webshopping.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shippo.Shippo;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.model.Address;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;


import edu.sru.cpsc.webshopping.domain.billing.ShippingAddress_Form;

@Service
public class AddressService {

    private final String shippoApiKey; // Retrieve this from a secure source

    @Autowired
    public AddressService(@Value("${shippo.api.key}") String apiKey) {
        this.shippoApiKey = apiKey;
        Shippo.setApiKey(shippoApiKey);
    }

    public boolean addressExists(ShippingAddress_Form shipping) {

        Shippo.setApiKey(shippoApiKey);

        HashMap<String, Object> addressMap = new HashMap<String, Object>();
        addressMap.put("name", shipping.getRecipient());
        addressMap.put("street1", shipping.getStreetAddress());
        addressMap.put("city", shipping.getCity());
        addressMap.put("state", shipping.getState().getStateName());
        addressMap.put("zip", shipping.getPostalCode());
        addressMap.put("country", "US");
        addressMap.put("validate", "true");

        try {
            Address shippoAddress = Address.create(addressMap);
            boolean isValid = shippoAddress.getValidationResults().getIsValid();
            if (!isValid) {
                System.out.println("Address: " + shippoAddress.toString() + " is invalid.");
                System.out.println("Address is invalid because: ");
                for (Address.ValidationMessage message : shippoAddress.getValidationResults().getValidationMessages()) {
                    System.out.println(message.getText());
                }
            }
            return isValid;

        } catch (InvalidRequestException | AuthenticationException | APIConnectionException | APIException e) {
            e.printStackTrace();
            return false;
        }
	}
    
}
