package edu.sru.cpsc.webshopping.repository.widgets.lawncare;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower;

public interface LawnCareLawnMowerRepository<T extends LawnCare_LawnMower> extends CrudRepository<T, Long> {

}
