package edu.sru.cpsc.webshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeRepository;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {
	@Autowired
    private AttributeRepository attributeRepository;

    @PostMapping("/add")
    public Attribute addAttribute(@Validated @RequestBody Attribute attribute, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Validation errors");
        }
        return attributeRepository.save(attribute);
    }

    /*
	 * gets entry of a attribute by ID
	 * @param Id of attribute to be retrieved
	 * @return attribute entry associated with ID
	 */
    @GetMapping("/{id}")
    public Attribute getAttribute(@PathVariable long id) {
        return attributeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attribute ID"));
    }
    
    // Read all Attributes
    @GetMapping("/all")
    public Iterable<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
    }

    // Update a Attribute by ID
    @PutMapping("/update/{id}")
    public Attribute updateAttribute(@PathVariable long id, @Validated @RequestBody Attribute newAttribute, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Validation errors");
        }
        return attributeRepository.findById(id)
                .map(attribute -> {
                	attribute.setAttributeKey(newAttribute.getAttributeKey());
                	attribute.setCategory(newAttribute.getCategory());
                    return attributeRepository.save(attribute);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid Attribute ID"));
    }

    // Delete a Attribute by ID
    @DeleteMapping("/delete/{id}")
    public void deleteAttribute(@PathVariable long id) {
    	Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attribute ID"));
    	attributeRepository.delete(attribute);
    }
}