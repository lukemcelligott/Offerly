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
 * Inherits from the appliances class and adds attributes for dryers.
 * @author NICK
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Appliance_Dryers extends Widget_Appliance{

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
	public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Dryers> dryers) {
		HashMap<String, HashSet<String>> dryer_items = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> dryer_length = new HashSet<String>();
		HashSet<String> dryer_width = new HashSet<String>();
		HashSet<String> dryer_height = new HashSet<String>();
		HashSet<String> dryer_color = new HashSet<String>();
		HashSet<String> dryer_condition = new HashSet<String>();
		HashSet<String> dryer_model = new HashSet<String>();
		HashSet<String> dryer_make = new HashSet<String>();
		HashSet<String> dryer_material = new HashSet<String>();
		for (Appliance_Dryers currDryer : dryers) {
			dryer_length.add(String.valueOf(currDryer.getLength()));
			dryer_width.add(String.valueOf(currDryer.getWidth()));
			dryer_height.add(String.valueOf(currDryer.getHeight()));
			dryer_color.add(currDryer.getColor());
			dryer_condition.add(currDryer.getCondition());
			dryer_model.add(currDryer.getModel());
			dryer_make.add(currDryer.getBrand());
			dryer_material.add(currDryer.getMaterial());
		}
		// Put unique lists into HashMap
		dryer_items.put("dryer_length", dryer_length);
		dryer_items.put("dryer_width", dryer_width);
		dryer_items.put("dryer_height", dryer_height);
		dryer_items.put("dryer_color", dryer_color);
		dryer_items.put("dryer_condition", dryer_condition);
		dryer_items.put("dryer_model", dryer_model);
		dryer_items.put("dryer_make", dryer_make);
		dryer_items.put("dryer_material", dryer_material);
		
		return dryer_items;
	}
	
	
}
