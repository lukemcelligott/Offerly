package edu.sru.cpsc.webshopping.repository.widgets;

import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.widgets.Subcategory;

public interface SubcategoryRepository extends CrudRepository<Subcategory, String> {
	Iterable<Subcategory> findByParent(String parentName);
}