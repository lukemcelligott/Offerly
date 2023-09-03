package edu.sru.cpsc.webshopping.repository.widgets;

import edu.sru.cpsc.webshopping.domain.widgets.WidgetAttribute;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

// Generic WidgetRepository that is to be used by Widget subtypes
// Work in progress, using as reference:
// https://blog.netgloo.com/2014/12/18/handling-entities-inheritance-with-spring-data-jpa/
public interface WidgetAttributeRepository extends CrudRepository<WidgetAttribute, Long> {
	
}
