package edu.sru.cpsc.webshopping.domain.widgets;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class WidgetAttribute {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Copy key from parent attribute
    private String key;
    
    // Value to be filled by user. key is
    private String value;

    @ManyToOne
    private Widget widget;
    
    @ManyToOne
    private Attribute attribute;
    
    public WidgetAttribute(Widget widget, Attribute attribute) {
    	this.attribute = attribute;
    	this.widget = widget;
    	this.key = attribute.getKey(); //set key to attribute key
    }
    
    // Getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
