package edu.sru.cpsc.webshopping.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import edu.sru.cpsc.webshopping.domain.widgets.Category;

@Service
public class CategoryService {

    public List<String> generateCategoryStack(Category category) {
        List<String> categoryStack = new ArrayList<>();
        while (category != null) {
            categoryStack.add(0, category.getName()); // Add to the beginning of the list
            category = category.getParent();
        }
        return categoryStack;
    }

}
