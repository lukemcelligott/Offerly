package edu.sru.cpsc.webshopping.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.widgets.Fields;
import edu.sru.cpsc.webshopping.repository.widgets.FieldsRepository;





@RestController
public class FieldsController{
	
	private FieldsRepository fieldsRepo;
	
	FieldsController(FieldsRepository fieldsRepo){
		this.fieldsRepo = fieldsRepo;
	}
	
	
	@RequestMapping("/get-all-fields")
	public Iterable<Fields> getAllFields(){
		return fieldsRepo.findAll();
	}
	
}