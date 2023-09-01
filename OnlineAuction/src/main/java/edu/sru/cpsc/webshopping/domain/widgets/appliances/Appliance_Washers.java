package edu.sru.cpsc.webshopping.domain.widgets.appliances;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;

/**
 * Inherits from the appliances class and adds attributes for washers.
 * @author NICK
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Appliance_Washers extends Widget_Appliance{

	@NonNull
	private String model;
	
	@NonNull
	private String brand;
	
	@NonNull
	private String material;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	/**
	 * Produces a HashMap, where the String is the name of a list of attributes, and the value
	 * is the unique values of all the attributes in the pasted Iterable
	 * @param dryers
	 * @return HashMap<String, HashSet<String>>
	 */
	public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Washers> washers) {
		HashMap<String, HashSet<String>> washer_items = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> washer_length = new HashSet<String>();
		HashSet<String> washer_width = new HashSet<String>();
		HashSet<String> washer_height = new HashSet<String>();
		HashSet<String> washer_color = new HashSet<String>();
		HashSet<String> washer_condition = new HashSet<String>();
		HashSet<String> washer_model = new HashSet<String>();
		HashSet<String> washer_make = new HashSet<String>();
		HashSet<String> washer_material = new HashSet<String>();
		for (Appliance_Washers currWasher : washers) {
			washer_length.add(String.valueOf(currWasher.getLength()));
			washer_width.add(String.valueOf(currWasher.getWidth()));
			washer_height.add(String.valueOf(currWasher.getHeight()));
			washer_color.add(currWasher.getColor());
			washer_condition.add(currWasher.getCondition());
			washer_model.add(currWasher.getModel());
			washer_make.add(currWasher.getBrand());
			washer_material.add(currWasher.getMaterial());
		}
		// Put unique lists into HashMap
		washer_items.put("washer_length", washer_length);
		washer_items.put("washer_width", washer_width);
		washer_items.put("washer_height", washer_height);
		washer_items.put("washer_color", washer_color);
		washer_items.put("washer_condition", washer_condition);
		washer_items.put("washer_model", washer_model);
		washer_items.put("washer_make", washer_make);
		washer_items.put("washer_material", washer_material);
		
		return washer_items;
	}
}
