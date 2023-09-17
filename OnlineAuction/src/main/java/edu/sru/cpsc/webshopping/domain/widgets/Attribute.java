package edu.sru.cpsc.webshopping.domain.widgets;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

    @ManyToOne
    private Category category;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
