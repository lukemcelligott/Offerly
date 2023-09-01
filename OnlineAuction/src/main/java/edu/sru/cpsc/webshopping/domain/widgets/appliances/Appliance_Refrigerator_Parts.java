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
@Table(name = "appliance_refrigerator_parts")
public class Appliance_Refrigerator_Parts extends Widget_Appliance_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Refrigerator_Parts> refrigeratorPart) {
		HashMap<String, HashSet<String>> refrigerator_parts = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		
		HashSet<String> refrigerator_part_model = new HashSet<String>();
		HashSet<String> refrigerator_part_brand = new HashSet<String>();
		HashSet<String> refrigerator_part_color = new HashSet<String>();
		HashSet<String> refrigerator_part_warranty = new HashSet<String>();
		HashSet<String> refrigerator_part_condition = new HashSet<String>();
		
		for (Appliance_Refrigerator_Parts currRefrigeratorPart : refrigeratorPart) {
			
			refrigerator_part_model.add(currRefrigeratorPart.getModel());
			refrigerator_part_brand.add(currRefrigeratorPart.getBrand());
			refrigerator_part_color.add(currRefrigeratorPart.getColor());
			refrigerator_part_warranty.add(currRefrigeratorPart.getWarranty());
			refrigerator_part_condition.add(currRefrigeratorPart.getCondition());
		}
		// Put unique lists into HashMap
		
		refrigerator_parts.put("refrigerator_part_model", refrigerator_part_model);
		refrigerator_parts.put("refrigerator_part_brand", refrigerator_part_brand);
		refrigerator_parts.put("refrigerator_part_color", refrigerator_part_color);
		refrigerator_parts.put("refrigerator_part_warranty", refrigerator_part_warranty);
		refrigerator_parts.put("refrigerator_part_condition", refrigerator_part_condition);
		
		return refrigerator_parts;
	}
}
