package edu.sru.cpsc.webshopping.domain.widgets;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class WidgetAttribute {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Copy key from parent attribute
    @NonNull
    private String attributeKey;
    
    // Value to be filled by user. key is
    private String value;

    @ManyToOne
    private Widget widget;
    
    @ManyToOne
    private Attribute attribute;
    
	public WidgetAttribute() {

	}

	public WidgetAttribute(Attribute attribute) {
    	this.attribute = attribute;
    	this.attributeKey = attribute.getAttributeKey(); //set key to attribute key
    }

    public WidgetAttribute(Widget widget, Attribute attribute) {
    	this.attribute = attribute;
    	this.widget = widget;
    	this.attributeKey = attribute.getAttributeKey(); //set key to attribute key
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

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
		this.attributeKey = attribute.getAttributeKey(); //set key to attribute key
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}
}
