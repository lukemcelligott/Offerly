package edu.sru.cpsc.webshopping.domain.widgets.appliances;

import java.util.HashMap;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * Inherits from the Appliance class, and adds blender attributes
 *
 * @author Stephen
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appliance_dryer_parts")
public class Appliance_Dryer_Parts extends Widget_Appliance_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Dryer_Parts> dryerPart) {
		HashMap<String, HashSet<String>> dryer_parts = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		
		HashSet<String> dryer_part_model = new HashSet<String>();
		HashSet<String> dryer_part_brand = new HashSet<String>();
		HashSet<String> dryer_part_color = new HashSet<String>();
		HashSet<String> dryer_part_warranty = new HashSet<String>();
		HashSet<String> dryer_part_condition = new HashSet<String>();
		
		for (Appliance_Dryer_Parts currDryerPart : dryerPart) {
			
			dryer_part_model.add(currDryerPart.getModel());
			dryer_part_brand.add(currDryerPart.getBrand());
			dryer_part_color.add(currDryerPart.getColor());
			dryer_part_warranty.add(currDryerPart.getWarranty());
			dryer_part_condition.add(currDryerPart.getCondition());
		}
		// Put unique lists into HashMap
		
		dryer_parts.put("dryer_part_model", dryer_part_model);
		dryer_parts.put("dryer_part_brand", dryer_part_brand);
		dryer_parts.put("dryer_part_color", dryer_part_color);
		dryer_parts.put("dryer_part_warranty", dryer_part_warranty);
		dryer_parts.put("dryer_part_condition", dryer_part_condition);
		
		return dryer_parts;
	}
}
