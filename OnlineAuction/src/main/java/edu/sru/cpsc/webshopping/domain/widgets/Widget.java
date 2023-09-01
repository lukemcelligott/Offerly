package edu.sru.cpsc.webshopping.domain.widgets;

import com.opencsv.bean.CsvIgnore;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * Widget class stores basic information such as name, description, and the category of the widget. Other widget classes inherit these from it.
 * @author NICK
 *
 */
// Types of Widgets that can be sold through the website
// This type will have different Widget subtypes, for each type of website sold
// Each Subtype will have more detailed information
@Entity
// We expect to have many different Widget subtypes, so JOINED is chosen to split each subtype's unique characteristics into a
// separate table
@Inheritance(strategy=InheritanceType.JOINED)
public class Widget {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NonNull
	private String name;
	
	@NonNull 
	private String description;
	
	@ManyToMany(mappedBy = "wishlistedWidgets")
	@CsvIgnore
	private Set<User> wishlistingUsers;
	
	private String category;
	
	private String subCategory;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		System.out.println(name);
		System.out.println(subCategory);
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
}