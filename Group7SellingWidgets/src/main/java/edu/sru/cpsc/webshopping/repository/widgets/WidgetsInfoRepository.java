package edu.sru.cpsc.webshopping.repository.widgets;

import edu.sru.cpsc.webshopping.domain.widgets.WidgetsInfo;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface WidgetsInfoRepository extends CrudRepository<WidgetsInfo, Long> {

  List<WidgetsInfo> findAllBySubCategoryEqualsIgnoreCase(String subCategory);

}
