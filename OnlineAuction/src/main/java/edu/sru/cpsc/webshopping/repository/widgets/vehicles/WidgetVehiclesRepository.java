package edu.sru.cpsc.webshopping.repository.widgets.vehicles;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.widgets.vehicles.Widget_Vehicles;

// Generic WidgetRepository that is to be used by Widget subtypes
// Work in progress, using as reference: https://blog.netgloo.com/2014/12/18/handling-entities-inheritance-with-spring-data-jpa/
public interface WidgetVehiclesRepository<T extends Widget_Vehicles> extends CrudRepository<T, Long> {

}