package edu.sru.cpsc.webshopping.repository.widgets.lawncare;

import edu.sru.cpsc.webshopping.domain.widgets.lawncare.LawnCare_LawnMower_Parts;
import org.springframework.data.repository.CrudRepository;

public interface LawnCareLawnMowerPartsRepository<T extends LawnCare_LawnMower_Parts>
    extends CrudRepository<T, Long> {}
