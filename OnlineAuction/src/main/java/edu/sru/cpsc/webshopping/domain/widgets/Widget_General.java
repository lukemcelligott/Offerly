package edu.sru.cpsc.webshopping.domain.widgets;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.lang.NonNull;

import edu.sru.cpsc.webshopping.domain.widgets.Widget;


/*
 * This widget type is added to give a framework in case of listing
 * Ideally, this will work alongside a startup routine declaring what type of widgets
 * are to be sold with the deployment
 * @author CODY 
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Widget_General extends Widget {
	
	
	private String color;
	
	@NonNull
	private String itemCondition;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}
	
}






