package edu.sru.cpsc.webshopping.repository.widgets.lawncare;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare;

public interface WidgetLawnCareRepository<T extends Widget_LawnCare> extends CrudRepository<T, Long> {

}
