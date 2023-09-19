package edu.sru.cpsc.webshopping.util;

import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PreLoad {

  private final CategoryRepository categoryRepository;

  @Autowired
  public PreLoad(CategoryRepository categoryRepository) {
      this.categoryRepository = categoryRepository;
  }

  /*
   * Import categories from a CSV file. CSV file from: https://support.google.com/merchants/answer/6324436?hl=en-GB
   * @return void
   */
  public void importCategoriesFromCSV() {
      String csvFilePath = "src/main/resources/static/preload/category-tree.csv";
      Map<String, Category> categoryMap = new HashMap<>();

      try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
          String line;
          while ((line = br.readLine()) != null) {
            // Check for BOM and remove it
            if (line.startsWith("\uFEFF")) {
              line = line.substring(1);
            }
            String[] values = line.split(",");

            // Filter out empty values and get the category and its parent
            List<String> nonEmptyValues = Arrays.stream(values).filter(v -> !v.trim().isEmpty()).collect(Collectors.toList());
            String categoryName = nonEmptyValues.get(nonEmptyValues.size() - 1);
            String parentName = nonEmptyValues.size() > 1 ? nonEmptyValues.get(nonEmptyValues.size() - 2) : null;

            Category category = new Category(categoryName);

            // If the category has a parent
            if (parentName != null) {
                Category parentCategory = categoryMap.get(parentName);
                category.setParent(parentCategory);
            }

            // Persist the category to the database
            categoryRepository.save(category);

            // Add the category to the map
            categoryMap.put(categoryName, category);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  }


  public static String getDefaultCountryCode() {

    try {
      Scanner sc = new Scanner(Paths.get("src/main/resources/static/preload/default_country.txt"));
      return sc.nextLine().trim();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
