package notonthehighstreet.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import notonthehighstreet.model.Item;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class CSVInMemory implements Serializable {

    /**
     * Returns {@link List} of {@link Item} converted from a CSV file.
     * @return {@link List} of {@link Item} converted from a CSV file
     * @throws FileNotFoundException the exception to be launched
     */
    public List<Item> readAndConvertItemsFromCSVFile() throws FileNotFoundException {
        var classLoader = getClass().getClassLoader();
        var file = new File(Objects.requireNonNull(classLoader.getResource("items.csv")).getFile());

        try (final Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath())))) {
            final CsvToBean<Item> csvToBean = new CsvToBeanBuilder(reader)
                  .withType(Item.class)
                  .withIgnoreLeadingWhiteSpace(true)
                  .build();

            return csvToBean.parse();
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
