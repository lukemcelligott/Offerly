package edu.sru.cpsc.webshopping.repository.widgets.appliances;

import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Blender;
import edu.sru.cpsc.webshopping.domain.widgets.appliances.Appliance_Blender_Parts;
import org.springframework.data.repository.CrudRepository;

// Generic WidgetRepository that is to be used by Widget subtypes
// Work in progress, using as reference: https://blog.netgloo.com/2014/12/18/handling-entities-inheritance-with-spring-data-jpa/
public interface ApplianceBlenderPartsRepository<T extends Appliance_Blender_Parts> extends CrudRepository<T, Long> {

}
