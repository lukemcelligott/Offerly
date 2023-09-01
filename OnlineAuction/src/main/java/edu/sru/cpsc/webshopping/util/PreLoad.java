package edu.sru.cpsc.webshopping.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class PreLoad {

  public static Set<String> subCategoryConfiguration() {
    return categoryConfiguration().stream().map(Pair::getRight).collect(Collectors.toSet());
  }

  public static Set<Pair<String, String>> categoryConfiguration() {
    try (Reader reader =
            Files.newBufferedReader(
                Paths.get("src/main/resources/static/preload/category_config.csv"));
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {
      return csvReader.readAll().stream()
          .map(row -> Pair.of(row[0], row[1]))
          .collect(Collectors.toSet());
    } catch (IOException | CsvException e) {
      throw new RuntimeException(e);
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

  private PreLoad() {}
}
