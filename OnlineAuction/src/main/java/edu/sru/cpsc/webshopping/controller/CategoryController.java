package edu.sru.cpsc.webshopping.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;





@RestController
public class CategoryController{
	
	private CategoryRepository categoryRepo;
	
	CategoryController(CategoryRepository categoryRepo){
		this.categoryRepo = categoryRepo;
	}
	
	/*
	 * Gets an Iterable collection of all of the Categories stored in the database
	 * Useful for creating a selection field of every category
	 * @return iterable of all categories
	 */
	@RequestMapping("/get-all-categories")
	public Iterable<Category> getAllCategories(){
		return categoryRepo.findAll();
	}
	
	/*
	 * gets entry of a category by name
	 * @param name name of category to be retrieved
	 * @return category entry associated with name
	 */
	@RequestMapping("/get-category-by-name/{name}")
	public Category getCategoryByName(@PathVariable("name") String name) {
		return categoryRepo.findById(name).orElseThrow(() -> new IllegalArgumentException("Invalid name passed to getCategoryByName."));
	}
	
}