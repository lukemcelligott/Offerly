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
@Table(name = "appliance_microwave_parts")
public class Appliance_Microwave_Parts extends Widget_Appliance_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Microwave_Parts> microwavePart) {
		HashMap<String, HashSet<String>> microwave_parts = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		
		HashSet<String> microwave_part_model = new HashSet<String>();
		HashSet<String> microwave_part_brand = new HashSet<String>();
		HashSet<String> microwave_part_color = new HashSet<String>();
		HashSet<String> microwave_part_warranty = new HashSet<String>();
		HashSet<String> microwave_part_condition = new HashSet<String>();
		
		for (Appliance_Microwave_Parts currMicrowavePart : microwavePart) {
			
			microwave_part_model.add(currMicrowavePart.getModel());
			microwave_part_brand.add(currMicrowavePart.getBrand());
			microwave_part_color.add(currMicrowavePart.getColor());
			microwave_part_warranty.add(currMicrowavePart.getWarranty());
			microwave_part_condition.add(currMicrowavePart.getCondition());
		}
		// Put unique lists into HashMap
		
		microwave_parts.put("microwave_part_model", microwave_part_model);
		microwave_parts.put("microwave_part_brand", microwave_part_brand);
		microwave_parts.put("microwave_part_color", microwave_part_color);
		microwave_parts.put("microwave_part_warranty", microwave_part_warranty);
		microwave_parts.put("microwave_part_condition", microwave_part_condition);
		
		return microwave_parts;
	}
}
