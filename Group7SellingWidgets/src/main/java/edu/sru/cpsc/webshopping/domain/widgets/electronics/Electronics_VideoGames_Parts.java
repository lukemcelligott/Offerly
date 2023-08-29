package edu.sru.cpsc.webshopping.domain.widgets.electronics;

import edu.sru.cpsc.webshopping.domain.widgets.appliances.Widget_Appliance_Parts;

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
@Table(name = "electronics_video_games_parts")
public class Electronics_VideoGames_Parts extends Widget_Electronics_Parts {

  @NonNull
  @Column(name = "`material`")
  private String material;

  @NonNull
  @Column(name = "`warranty`")
  private String warranty;

  @NonNull
  @Column(name = "`condition`")
  private String condition;
  
  public static HashMap<String, HashSet<String>> getAttributes(Iterable<Electronics_VideoGames_Parts> videoGameAccessory) {
		HashMap<String, HashSet<String>> videoGame_accessory = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		
		HashSet<String> videoGame_accessory_model = new HashSet<String>();
		HashSet<String> videoGame_accessory_brand = new HashSet<String>();
		HashSet<String> videoGame_accessory_color = new HashSet<String>();
		HashSet<String> videoGame_accessory_warranty = new HashSet<String>();
		HashSet<String> videoGame_accessory_condition = new HashSet<String>();
		
		for (Electronics_VideoGames_Parts currVideoGameAccessory : videoGameAccessory) {
			
			videoGame_accessory_model.add(String.valueOf(currVideoGameAccessory.getModel()));
			videoGame_accessory_brand.add(String.valueOf(currVideoGameAccessory.getBrand()));
			videoGame_accessory_color.add(currVideoGameAccessory.getColor());
			videoGame_accessory_warranty.add(currVideoGameAccessory.getWarranty());
			videoGame_accessory_condition.add(currVideoGameAccessory.getCondition());
		}
		// Put unique lists into HashMap
		
		videoGame_accessory.put("videoGame_accessory_model", videoGame_accessory_model);
		videoGame_accessory.put("videoGame_accessory_brand", videoGame_accessory_brand);
		videoGame_accessory.put("videoGame_accessory_color", videoGame_accessory_color);
		videoGame_accessory.put("videoGame_accessory_warranty", videoGame_accessory_warranty);
		videoGame_accessory.put("videoGame_accessory_condition", videoGame_accessory_condition);
		
		return videoGame_accessory;
	}
}
