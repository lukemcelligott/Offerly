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
 * Inherits from the Appliance class, and adds blender attributes
 * @author Cody
 *
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Appliance_Blender extends Widget_Appliance {
	
	@NonNull
	private String model;
	
	@NonNull
	private String brand;
	
	@NonNull
	private String material;
	
	@NonNull
	private float capacity;

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
	
	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}
	
	public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Blender> blenders) {
		HashMap<String, HashSet<String>> blender_items = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> blender_length = new HashSet<String>();
		HashSet<String> blender_width = new HashSet<String>();
		HashSet<String> blender_height = new HashSet<String>();
		HashSet<String> blender_color = new HashSet<String>();
		HashSet<String> blender_condition = new HashSet<String>();
		HashSet<String> blender_model = new HashSet<String>();
		HashSet<String> blender_brand = new HashSet<String>();
		HashSet<String> blender_material = new HashSet<String>();
		
		for (Appliance_Blender currBlender : blenders) {
			blender_length.add(String.valueOf(currBlender.getLength()));
			blender_width.add(String.valueOf(currBlender.getWidth()));
			blender_height.add(String.valueOf(currBlender.getHeight()));
			blender_color.add(currBlender.getColor());
			blender_condition.add(currBlender.getCondition());
			blender_model.add(currBlender.getModel());
			blender_brand.add(currBlender.getBrand());
			blender_material.add(currBlender.getMaterial());
		}
		// Put unique lists into HashMap
		blender_items.put("blender_length", blender_length);
		blender_items.put("blender_width", blender_width);
		blender_items.put("blender_height", blender_height);
		blender_items.put("blender_color", blender_color);
		blender_items.put("blender_condition", blender_condition);
		blender_items.put("blender_model", blender_model);
		blender_items.put("blender_brand", blender_brand);
		blender_items.put("blender_material", blender_material);
		
		return blender_items;
	}
	
	
}

