package edu.sru.cpsc.webshopping.domain.widgets;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Fields{
	
	@Id
	private String name;
	private String display;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
}