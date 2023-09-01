package edu.sru.cpsc.webshopping.domain.widgets.vehicles;

import edu.sru.cpsc.webshopping.domain.widgets.Widget;
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
 * Inherits from the widget class and adds attributes for appliances.
 *
 * @author Stephen
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "widget_vehicles_parts")
public class Widget_Vehicles_Parts extends Widget {

  @NonNull
  @Column(name = "`part_name`")
  private String partName;

  @NonNull
  @Column(name = "`model`")
  private String model;

  @NonNull
  @Column(name = "`brand`")
  private String brand;

  @NonNull
  @Column(name = "`color`")
  private String color;

  @NonNull
  @Column(name = "`made_in`")
  private String madeIn;
  
  @NonNull
  @Column(name = "`year`")
  private int year;
  
  public String getPartName()
  {
	  return partName;
  }
  
  public void setPartName(String partName)
  {
	  this.partName = partName;
  }
  
  public String getModel()
  {
	  return model;
  }
  
  public void setModel(String model)
  {
	  this.model = model;
  }
  
  public String getBrand()
  {
	  return brand;
  }
  
  public void setBrand(String brand)
  {
	  this.brand = brand;
  }
  
  public String getColor()
  {
	  return color;
  }
  
  public void setColor(String color)
  {
	  this.color = color;
  }
  
  public String getMadeIn()
  {
	  return madeIn;
  }
  
  public void setMadeIn(String madeIn)
  {
	  this.madeIn = madeIn;
  }
  
  public int getYear()
  {
	  return year;
  }
  
  public void setYear(int year)
  {
	  this.year = year;
  }
}
