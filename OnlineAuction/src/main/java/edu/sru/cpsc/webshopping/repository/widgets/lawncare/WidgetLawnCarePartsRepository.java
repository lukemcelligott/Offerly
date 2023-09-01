package edu.sru.cpsc.webshopping.repository.widgets.lawncare;

import edu.sru.cpsc.webshopping.domain.widgets.lawncare.Widget_LawnCare_Parts;
import org.springframework.data.repository.CrudRepository;

public interface WidgetLawnCarePartsRepository<T extends Widget_LawnCare_Parts>
    extends CrudRepository<T, Long> {}
