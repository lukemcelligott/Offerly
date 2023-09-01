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
 * Inherits from the appliances class and adds attributes for refrigerators.
 * @author NICK
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Appliance_Refrigerator extends Widget_Appliance{

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
	
	public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Refrigerator> fridges) {
		HashMap<String, HashSet<String>> fridge_items = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> fridge_length = new HashSet<String>();
		HashSet<String> fridge_width = new HashSet<String>();
		HashSet<String> fridge_height = new HashSet<String>();
		HashSet<String> fridge_color = new HashSet<String>();
		HashSet<String> fridge_condition = new HashSet<String>();
		HashSet<String> fridge_model = new HashSet<String>();
		HashSet<String> fridge_make = new HashSet<String>();
		HashSet<String> fridge_material = new HashSet<String>();
		for (Appliance_Refrigerator currFridge : fridges) {
			fridge_length.add(String.valueOf(currFridge.getLength()));
			fridge_width.add(String.valueOf(currFridge.getWidth()));
			fridge_height.add(String.valueOf(currFridge.getHeight()));
			fridge_color.add(currFridge.getColor());
			fridge_condition.add(currFridge.getCondition());
			fridge_model.add(currFridge.getModel());
			fridge_make.add(currFridge.getBrand());
			fridge_material.add(currFridge.getMaterial());
		}
		// Put unique lists into HashMap
		fridge_items.put("fridge_length", fridge_length);
		fridge_items.put("fridge_width", fridge_width);
		fridge_items.put("fridge_height", fridge_height);
		fridge_items.put("fridge_color", fridge_color);
		fridge_items.put("fridge_condition", fridge_condition);
		fridge_items.put("fridge_model", fridge_model);
		fridge_items.put("fridge_make", fridge_make);
		fridge_items.put("fridge_material", fridge_material);
		
		return fridge_items;
	}
}
