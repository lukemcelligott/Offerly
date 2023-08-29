package edu.sru.cpsc.webshopping.domain.widgets.lawncare;

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
@Table(name = "widget_lawn_care_parts")
public class Widget_LawnCare_Parts extends Widget {

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
}
