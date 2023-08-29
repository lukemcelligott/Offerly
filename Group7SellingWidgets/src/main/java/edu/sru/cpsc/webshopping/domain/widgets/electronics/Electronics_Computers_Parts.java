package edu.sru.cpsc.webshopping.domain.widgets.electronics;

import edu.sru.cpsc.webshopping.domain.widgets.appliances.Widget_Appliance_Parts;
import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Vehicle_Car_Parts;

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
@Table(name = "electronics_computers_parts")
public class Electronics_Computers_Parts extends Widget_Electronics_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<Electronics_Computers_Parts> computerPart) {
		HashMap<String, HashSet<String>> computer_parts = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		
		HashSet<String> computer_part_model = new HashSet<String>();
		HashSet<String> computer_part_brand = new HashSet<String>();
		HashSet<String> computer_part_color = new HashSet<String>();
		HashSet<String> computer_part_warranty = new HashSet<String>();
		HashSet<String> computer_part_condition = new HashSet<String>();
		
		for (Electronics_Computers_Parts currComputerPart : computerPart) {
			
			computer_part_model.add(String.valueOf(currComputerPart.getModel()));
			computer_part_brand.add(String.valueOf(currComputerPart.getBrand()));
			computer_part_color.add(currComputerPart.getColor());
			computer_part_warranty.add(currComputerPart.getWarranty());
			computer_part_condition.add(currComputerPart.getCondition());
		}
		// Put unique lists into HashMap
		
		computer_parts.put("computer_part_model", computer_part_model);
		computer_parts.put("computer_part_brand", computer_part_brand);
		computer_parts.put("computer_part_color", computer_part_color);
		computer_parts.put("computer_part_warranty", computer_part_warranty);
		computer_parts.put("computer_part_condition", computer_part_condition);
		
		return computer_parts;
	}
}
