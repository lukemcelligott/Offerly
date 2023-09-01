package edu.sru.cpsc.webshopping.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.widgets.ItemCondition;
import edu.sru.cpsc.webshopping.repository.widgets.ItemConditionRepository;

@RestController
public class ItemConditionController{
	private ItemConditionRepository conditionRepo;
	
	ItemConditionController(ItemConditionRepository conditionRepo) {
		this.conditionRepo = conditionRepo;
	}
	
	/*
	 * Gets an Iterable collection of all of the Conditions stored in the database
	 * Useful for creating a selection field of every condition
	 * @return iterable of all conditions
	 */
	@RequestMapping("/get-all-conditions")
	public Iterable<ItemCondition> getAllConditions(){
		return conditionRepo.findAll();
	}
	
	/*
	 * gets entry of a condition by rating
	 * @param rating number rating of condition to be retrieved
	 * @return condition entry associated with rating
	 */
	@RequestMapping("/get-condition-by-rating/{rating}")
	public ItemCondition getConditionByRating(@PathVariable("rating") int rating) {
		return conditionRepo.findById(rating).orElseThrow(() -> new IllegalArgumentException("Invalid rating passed to getConditionByRating: needs int from 1-5"));
	}
	
	
}







