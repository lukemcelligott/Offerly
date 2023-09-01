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
@Table(name = "appliance_blender_parts")
public class Appliance_Blender_Parts extends Widget_Appliance_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<Appliance_Blender_Parts> blenderPart) {
		HashMap<String, HashSet<String>> blender_parts = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		
		HashSet<String> blender_part_model = new HashSet<String>();
		HashSet<String> blender_part_brand = new HashSet<String>();
		HashSet<String> blender_part_color = new HashSet<String>();
		HashSet<String> blender_part_warranty = new HashSet<String>();
		HashSet<String> blender_part_condition = new HashSet<String>();
		
		for (Appliance_Blender_Parts currBlenderPart : blenderPart) {
			
			blender_part_model.add(currBlenderPart.getModel());
			blender_part_brand.add(currBlenderPart.getBrand());
			blender_part_color.add(currBlenderPart.getColor());
			blender_part_warranty.add(currBlenderPart.getWarranty());
			blender_part_condition.add(currBlenderPart.getCondition());
		}
		// Put unique lists into HashMap
		
		blender_parts.put("blender_part_model", blender_part_model);
		blender_parts.put("blender_part_brand", blender_part_brand);
		blender_parts.put("blender_part_color", blender_part_color);
		blender_parts.put("blender_part_warranty", blender_part_warranty);
		blender_parts.put("blender_part_condition", blender_part_condition);
		
		return blender_parts;
	}
}
