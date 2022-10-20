package notonthehighstreet.service;

import notonthehighstreet.model.Item;

import java.math.RoundingMode;

import static notonthehighstreet.constants.RulesConstants.SCALE;

public class Checkout {

    private final PromotionalRulesService promotionalRulesService;

    /**
     * Constructor parametrized
     * @param promotionalRulesService the rules to be applied.
     */
    public Checkout(final PromotionalRulesService promotionalRulesService) {
        this.promotionalRulesService = promotionalRulesService;
    }

    /**
     * Scan an item and add it to the cart.
     * @param item the item to be scanned
     */
    public void scan(final Item item) {
        promotionalRulesService.addItemToCart(item);
    }

    /**
     * Return the total price after the rules applied.
     * @return the total price after the rules applied
     */
    public Double total() {
        promotionalRulesService.applyRules();
        return promotionalRulesService.getTotalValueAfterDiscount().setScale(SCALE, RoundingMode.UP).doubleValue();
    }
}