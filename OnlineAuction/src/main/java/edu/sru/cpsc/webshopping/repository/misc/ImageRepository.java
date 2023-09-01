package edu.sru.cpsc.webshopping.repository.misc;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.misc.Image;
import edu.sru.cpsc.webshopping.domain.widgets.Subcategory;

public interface ImageRepository extends CrudRepository<Image, Long> {
	Image findByImageName(String imageName);
}