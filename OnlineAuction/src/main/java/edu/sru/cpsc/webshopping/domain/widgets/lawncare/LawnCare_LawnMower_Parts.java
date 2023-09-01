package edu.sru.cpsc.webshopping.domain.widgets.lawncare;

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
@Table(name = "lawn_care_lawn_mower_parts")
public class LawnCare_LawnMower_Parts extends Widget_LawnCare_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
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
  
  public String getMaterial()
  {
	  return material;
  }
  
  public void setMaterial(String material)
  {
	  this.material = material;
  }
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<LawnCare_LawnMower_Parts> mowerPart) {
		HashMap<String, HashSet<String>> mower_parts = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> mower_part_material = new HashSet<String>();
		HashSet<String> mower_part_condition = new HashSet<String>();
		HashSet<String> mower_part_warranty = new HashSet<String>();
		HashSet<String> mower_part_model = new HashSet<String>();
		HashSet<String> mower_part_brand = new HashSet<String>();
		for (LawnCare_LawnMower_Parts currMowerPart : mowerPart) {
			mower_part_material.add(currMowerPart.getMaterial());
			mower_part_condition.add(currMowerPart.getCondition());
			mower_part_warranty.add(currMowerPart.getWarranty());
			mower_part_model.add(currMowerPart.getModel());
			mower_part_brand.add(currMowerPart.getBrand());
		}
		// Put unique lists into HashMap
		mower_parts.put("mower_part_material", mower_part_material);
		mower_parts.put("mower_part_condition", mower_part_condition);
		mower_parts.put("mower_part_warranty", mower_part_warranty);
		mower_parts.put("mower_part_model", mower_part_model);
		mower_parts.put("mower_part_brand", mower_part_brand);

		
		return mower_parts;
	}
}
