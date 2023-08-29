package edu.sru.cpsc.webshopping.domain.widgets.vehicles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
 * Inherits from vehicle class and adds attributes for cars.
 * @author NICK
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Vehicle_Car extends Widget_Vehicles{

	@NonNull
	private String wheelDrive;
	
	@NonNull
	private String transmissionType;
	
	@NonNull
	private String model;
	
	@NonNull
	private String make;
	
	@NonNull
	private int year;

	public String getWheelDrive() {
		return wheelDrive;
	}

	public void setWheelDrive(String wheelDrive) {
		this.wheelDrive = wheelDrive;
	}

	public String getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public static HashMap<String, HashSet<String>> getAttributes(Iterable<Vehicle_Car> cars) {
		HashMap<String, HashSet<String>> car_items = new HashMap<String, HashSet<String>>();
		// HashSets used because all values are unique
		HashSet<String> car_model = new HashSet<String>();
		HashSet<String> car_make = new HashSet<String>();
		HashSet<String> car_year = new HashSet<String>();
		for (Vehicle_Car currCar : cars) {
			car_model.add(currCar.getModel());
			car_make.add(currCar.getMake());
			car_year.add(String.valueOf(currCar.getYear()));
		}
		// Put unique lists into HashMap
		car_items.put("car_year", car_year);
		car_items.put("car_model", car_model);
		car_items.put("car_make", car_make);

		
		return car_items;
	}
}
