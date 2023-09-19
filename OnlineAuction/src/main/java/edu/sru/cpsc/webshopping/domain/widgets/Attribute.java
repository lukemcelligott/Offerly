package edu.sru.cpsc.webshopping.domain.widgets;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.util.enums.AttributeDataType;

@Entity
public class Attribute {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // attribute to be filled by user. Value is determined when WidgetAttribute is created.
    @NonNull
    private String attributeKey;

	@Enumerated(EnumType.STRING)
	private AttributeDataType dataType;

    @OneToMany(mappedBy = "attribute", fetch = FetchType.EAGER)
    private List<AttributeRecommendation> categories;

	public Attribute() {}

	public Attribute(String name, AttributeDataType dataType) {
		this.attributeKey = name;
		this.dataType = dataType;
	}

    // Getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAttributeKey() {
		return attributeKey;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}

	public AttributeDataType getDataType() {
		return dataType;
	}

	public void setDataType(AttributeDataType dataType) {
		this.dataType = dataType;
	}

	public List<AttributeRecommendation> getCategories() {
		return categories;
	}
	public void setCategories(List<AttributeRecommendation> categories) {
		this.categories = categories;
	}
}
