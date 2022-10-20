package notonthehighstreet.service;

import notonthehighstreet.exception.BusinessException;
import notonthehighstreet.exception.InvalidProductCodeException;
import notonthehighstreet.model.Item;
import notonthehighstreet.repository.ItemRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * Constructor.
     */
    public ItemService() {
        itemRepository = new ItemRepository();
    }

    /**
     * Returns an item by its code.
     * @param code the item code to be filtered.
     * @return an item by its code
     * @throws BusinessException the exception to be launched
     */
    public Item getItemByCode(final String code) throws BusinessException {
        return getPredefinedItems().stream()
               .filter(item -> item.getCode().equals(code))
               .findFirst()
               .orElseThrow(InvalidProductCodeException::new);
    }

    /**
     * Returns a list of items.
     * @return a list of items
     * @throws BusinessException the exception to be launched
     */
    public List<Item> getPredefinedItems() throws BusinessException {
        try {
            return itemRepository.getPredefinedItems();
        } catch (final FileNotFoundException e) {
            throw new BusinessException("Error getting list of items", e);
        }
    }

    /**
     * Add a list of product codes to be scanned.
     * @param productCodes the list of product codes
     * @return list of {@link Item}
     */
    public List<Item> addProductsOnCartByListOfCodes(final List<String> productCodes) {
        final List<Item> items = new ArrayList<>();
        for (var code : productCodes) {
            try {
                items.add(getItemByCode(code));
            } catch (final Exception e) {
                throw new InvalidProductCodeException();
            }
        }
        return items;
    }
}
