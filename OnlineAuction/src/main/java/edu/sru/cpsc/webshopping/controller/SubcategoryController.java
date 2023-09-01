package edu.sru.cpsc.webshopping.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.widgets.Subcategory;
import edu.sru.cpsc.webshopping.repository.widgets.SubcategoryRepository;





@RestController
public class SubcategoryController{
	
	private SubcategoryRepository subcategoryRepo;
	
	SubcategoryController(SubcategoryRepository subcategoryRepo){
		this.subcategoryRepo = subcategoryRepo;
	}
	
	/*
	 * Gets an Iterable collection of all of the subcategories stored in the database
	 * Useful for creating a selection field of every subcategory
	 * @return iterable of all subcategories
	 */
	@RequestMapping("/get-all-subcategories")
	public Iterable<Subcategory> getAllSubcategories(){
		return subcategoryRepo.findAll();
	}
	
	/*
	 * gets entry of a subcategory by name
	 * @param name name of subcategory to be retrieved
	 * @return subcategory entry associated with name
	 */
	@RequestMapping("/get-subcategory-by-name/{name}")
	public Subcategory getSubcategoryByName(@PathVariable("name") String name) {
		return subcategoryRepo.findById(name).orElseThrow(() -> new IllegalArgumentException("Invalid name passed to getSubcategoryByName."));
	}
	
	/*
	 * gets all subcategories associated with a parent category
	 * @param parent name of desired parent category
	 * @return iterable of all subcategories corresponding to a parent category
	 */
	@RequestMapping("/get-subcategories-by-parent/{parent}")
	public Iterable<Subcategory> getSubcategoriesByParent(@PathVariable("parent") String parent){
		return subcategoryRepo.findByParent(parent);
	}
	
	
	
}