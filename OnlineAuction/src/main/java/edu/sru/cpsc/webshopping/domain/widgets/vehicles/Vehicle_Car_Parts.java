package edu.sru.cpsc.webshopping.domain.widgets.vehicles;

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
@Table(name = "vehicle_car_parts")
public class Vehicle_Car_Parts extends Widget_Vehicles_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
  public String getMaterial()
  {
	  return material;
  }
  
  public void setMaterial(String material)
  {
	  this.material = material;
  }
  
  public String getWarranty()
  {
	  return warranty;
  }
  
  public void setWarranty(String warranty)
  {
	  this.warranty = warranty;
  }
  
  public String getCondition()
  {
	  return condition;
  }
  
  public void setCondition(String condition)
  {
	  this.condition = condition;
  }
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<Vehicle_Car_Parts> carPart) {
		HashMap<String, HashSet<String>> car_parts = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		
		HashSet<String> car_part_model = new HashSet<String>();
		HashSet<String> car_part_brand = new HashSet<String>();
		HashSet<String> car_part_madeIn = new HashSet<String>();
		HashSet<String> car_part_material = new HashSet<String>();
		HashSet<String> car_part_warranty = new HashSet<String>();
		HashSet<String> car_part_condition = new HashSet<String>();
		
		for (Vehicle_Car_Parts currCarPart : carPart) {
			
			car_part_model.add(currCarPart.getModel());
			car_part_brand.add(currCarPart.getBrand());
			car_part_madeIn.add(currCarPart.getMadeIn());
			car_part_material.add(currCarPart.getMaterial());
			car_part_warranty.add(currCarPart.getWarranty());
			car_part_condition.add(currCarPart.getCondition());
		}
		// Put unique lists into HashMap
		
		car_parts.put("car_part_model", car_part_model);
		car_parts.put("car_part_brand", car_part_brand);
		car_parts.put("car_part_madeIn", car_part_madeIn);
		car_parts.put("car_part_material", car_part_material);
		car_parts.put("car_part_warranty", car_part_warranty);
		car_parts.put("car_part_condition", car_part_condition);
		
		return car_parts;
	}
}
