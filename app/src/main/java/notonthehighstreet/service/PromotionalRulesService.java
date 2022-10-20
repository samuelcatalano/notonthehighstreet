package notonthehighstreet.service;

import notonthehighstreet.model.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static notonthehighstreet.constants.RulesConstants.*;

public class PromotionalRulesService {

    private final List<Item> items;
    private List<Item> itemsDiscounted;
    private BigDecimal totalValueAfterDiscount = BigDecimal.ZERO;

    /**
     * Constructor.
     */
    public PromotionalRulesService() {
        items = new ArrayList<>();
        itemsDiscounted = new ArrayList<>();
    }

    /**
     * Adds an item to the items list.
     * @param item the item to be added
     */
    public void addItemToCart(final Item item) {
        items.add(item);
    }

    /**
     * Applies the defined rules.
     * @return an list of items with the rules applied
     */
    public List<Item> applyRules() {
        setTravelCardHolderRule();
        setTotalPriceRule();
        return itemsDiscounted;
    }

    /**
     * Set the travel card holder rule.
     */
    private void setTravelCardHolderRule() {
        var countTravelCards = items.stream().filter(item -> item.getCode().equals(TRAVEL_CARD_CODE)).count();
        if (countTravelCards >= 2) {
            for (var item : items) {
                if (item.getCode().equals(TRAVEL_CARD_CODE)) {
                    var itemDiscounted = Item.builder().price(DISCOUNTED_PRICE).build();
                    itemsDiscounted.add(itemDiscounted);
                } else {
                    itemsDiscounted.add(item);
                }
            }
        } else {
            itemsDiscounted = items;
        }
    }

    /**
     * Set the total price rule.
     */
    private void setTotalPriceRule() {
        var total = itemsDiscounted.stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.compareTo(SPEND_OVER_SIXTY) > 0) {
            var discount = getDiscountPercentage(total);
            totalValueAfterDiscount = total.subtract(discount);
        } else {
            totalValueAfterDiscount = total;
        }
    }

    /**
     * Calculate the discount percentage.
     * @param value the value that will have the discount applied
     * @return the value that will have the discount applied
     */
    private BigDecimal getDiscountPercentage(final BigDecimal value){
        return value.multiply(DISCOUNT_PERCENTAGE).divide(ONE_HUNDRED, RoundingMode.DOWN);
    }

    /**
     * Returns the total value after the rules applied.
     * @return the total value after the rules applied
     */
    public BigDecimal getTotalValueAfterDiscount() {
        return totalValueAfterDiscount;
    }
}
