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

import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/add")
    public Category addCategory(@Validated @RequestBody Category category, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Validation errors");
        }
        return categoryRepository.save(category);
    }

    /*
	 * gets entry of a category by ID
	 * @param Id of category to be retrieved
	 * @return category entry associated with ID
	 */
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
    }
    
    /*
	 * gets entry of a category by name
	 * @param  name of category to be retrieved
	 * @return category entry associated with name
	 */
	/*
	 * @GetMapping("/{name}") public Category
	 * getCategoryByName(@PathVariable("name") String name) { return
	 * categoryRepository.findById(name).orElseThrow(() -> new
	 * IllegalArgumentException("Invalid name passed to getCategoryByName.")); }
	 */

    // Read all Categories
    @GetMapping("/all")
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Update a Category by ID
    @PutMapping("/update/{id}")
    public Category updateCategory(@PathVariable long id, @Validated @RequestBody Category newCategory, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Validation errors");
        }
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(newCategory.getName());
                    category.setParent(newCategory.getParent());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
    }

    // Delete a Category by ID
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        categoryRepository.delete(category);
    }
}