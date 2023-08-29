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
@Table(name = "appliance_washer_parts")
public class Appliance_Washers_Parts extends Widget_Appliance_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Washers_Parts> washerPart) {
		HashMap<String, HashSet<String>> washer_parts = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		
		HashSet<String> washer_part_model = new HashSet<String>();
		HashSet<String> washer_part_brand = new HashSet<String>();
		HashSet<String> washer_part_color = new HashSet<String>();
		HashSet<String> washer_part_warranty = new HashSet<String>();
		HashSet<String> washer_part_condition = new HashSet<String>();
		
		for (Appliance_Washers_Parts currWasherPart : washerPart) {
			
			washer_part_model.add(currWasherPart.getModel());
			washer_part_brand.add(currWasherPart.getBrand());
			washer_part_color.add(currWasherPart.getColor());
			washer_part_warranty.add(currWasherPart.getWarranty());
			washer_part_condition.add(currWasherPart.getCondition());
		}
		// Put unique lists into HashMap
		
		washer_parts.put("washer_part_model", washer_part_model);
		washer_parts.put("washer_part_brand", washer_part_brand);
		washer_parts.put("washer_part_color", washer_part_color);
		washer_parts.put("washer_part_warranty", washer_part_warranty);
		washer_parts.put("washer_part_condition", washer_part_condition);
		
		return washer_parts;
	}
}
