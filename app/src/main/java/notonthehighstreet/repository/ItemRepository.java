package notonthehighstreet.repository;

import notonthehighstreet.model.Item;
import notonthehighstreet.utils.CSVInMemory;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

public class ItemRepository implements Serializable {

    private final CSVInMemory csvInMemory;

    /**
     * Constructor.
     */
    public ItemRepository() {
        csvInMemory = new CSVInMemory();
    }

    /**
     * Returns a list of items.
     * @return a list of items
     * @throws FileNotFoundException the exception to be launched
     */
    public List<Item> getPredefinedItems() throws FileNotFoundException {
        return csvInMemory.readAndConvertItemsFromCSVFile();
    }
}
