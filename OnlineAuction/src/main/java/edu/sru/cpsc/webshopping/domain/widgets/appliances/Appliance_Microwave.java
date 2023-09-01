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
 * Inherits from the appliances class and adds attributes for microwaves.
 * @author NICK
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Appliance_Microwave extends Widget_Appliance{

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
	
	public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Microwave> microwaves) {
		HashMap<String, HashSet<String>> microwave_items = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> microwave_length = new HashSet<String>();
		HashSet<String> microwave_width = new HashSet<String>();
		HashSet<String> microwave_height = new HashSet<String>();
		HashSet<String> microwave_color = new HashSet<String>();
		HashSet<String> microwave_condition = new HashSet<String>();
		HashSet<String> microwave_model = new HashSet<String>();
		HashSet<String> microwave_make = new HashSet<String>();
		HashSet<String> microwave_material = new HashSet<String>();
		for (Appliance_Microwave currMicrowave : microwaves) {
			microwave_length.add(String.valueOf(currMicrowave.getLength()));
			microwave_width.add(String.valueOf(currMicrowave.getWidth()));
			microwave_height.add(String.valueOf(currMicrowave.getHeight()));
			microwave_color.add(currMicrowave.getColor());
			microwave_condition.add(currMicrowave.getCondition());
			microwave_model.add(currMicrowave.getModel());
			microwave_make.add(currMicrowave.getBrand());
			microwave_material.add(currMicrowave.getMaterial());
		}
		// Put unique lists into HashMap
		microwave_items.put("microwave_length", microwave_length);
		microwave_items.put("microwave_width", microwave_width);
		microwave_items.put("microwave_height", microwave_height);
		microwave_items.put("microwave_color", microwave_color);
		microwave_items.put("microwave_condition", microwave_condition);
		microwave_items.put("microwave_model", microwave_model);
		microwave_items.put("microwave_make", microwave_make);
		microwave_items.put("microwave_material", microwave_material);
		
		return microwave_items;
	}
}
