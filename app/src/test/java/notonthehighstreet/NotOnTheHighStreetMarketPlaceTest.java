package notonthehighstreet;

import notonthehighstreet.exception.InvalidProductCodeException;
import notonthehighstreet.model.Item;
import notonthehighstreet.service.ItemService;
import notonthehighstreet.service.PromotionalRulesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotOnTheHighStreetMarketPlaceTest {

    private final ItemService itemService;
    private final PromotionalRulesService promotionRules;

    private Integer precision;
    private RoundingMode roundingMode;

    private static final String TRAVEL_CARD_HOLDER = "Travel Card Holder";
    private static final String PERSONALISED_CUFFLINKS = "Personalised cufflinks";
    private static final String KIDS_T_SHIRT = "Kids T-shirt";
    public static final String EXCEPTION_MESSAGE = "One or more products have invalid codes!";

    private static final Double PREDEFINED_ITEMS_AMOUNT = 74.20;
    private static final Double SPENT_OVER_SIXTY_ITEMS_AMOUNT = 66.78;
    private static final Double TWO_MORE_CARDS_ITEMS_AMOUNT = 36.95;

    NotOnTheHighStreetMarketPlaceTest() {
        itemService = new ItemService();
        promotionRules = new PromotionalRulesService();
    }

    @BeforeEach
    void setup() {
        precision = 2;
        roundingMode = RoundingMode.UP;
    }

    @Test
    void test_validate_predefined_items_size() throws Exception {
        var items = itemService.getPredefinedItems();
        assertThat(items.size(), is(3));
    }

    @Test
    void test_validate_amount_predefined_items() throws Exception {
        var items = itemService.getPredefinedItems();
        var amountGiven = round(items.stream().map(Item::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        var amountExpected = new BigDecimal(PREDEFINED_ITEMS_AMOUNT).setScale(precision, RoundingMode.DOWN);

        assertThat(amountGiven, is(amountExpected));
    }

    @Test
    void test_validate_spend_over_sixty() {
        promotionRules.addItemToCart(new Item("001", TRAVEL_CARD_HOLDER, new BigDecimal("9.25")));
        promotionRules.addItemToCart(new Item("002", PERSONALISED_CUFFLINKS, new BigDecimal("45.00")));
        promotionRules.addItemToCart(new Item("003", KIDS_T_SHIRT, new BigDecimal("19.95")));

        promotionRules.applyRules();

        var amountGiven = promotionRules.getTotalValueAfterDiscount().setScale(precision, RoundingMode.UP);
        var amountExpected = new BigDecimal(SPENT_OVER_SIXTY_ITEMS_AMOUNT).setScale(precision, RoundingMode.DOWN);

        assertThat(amountGiven, is(amountExpected));
    }

    @Test
    void test_validate_two_or_more_travel_card_holders() {
        promotionRules.addItemToCart(new Item("001", TRAVEL_CARD_HOLDER, new BigDecimal("9.25")));
        promotionRules.addItemToCart(new Item("003", KIDS_T_SHIRT, new BigDecimal("19.95")));
        promotionRules.addItemToCart(new Item("001", TRAVEL_CARD_HOLDER, new BigDecimal("9.25")));

        promotionRules.applyRules();

        var amountGiven = promotionRules.getTotalValueAfterDiscount().setScale(precision, RoundingMode.UP);
        var amountExpected = new BigDecimal(TWO_MORE_CARDS_ITEMS_AMOUNT).setScale(precision, RoundingMode.DOWN);

        assertThat(amountGiven, is(amountExpected));
    }

    @Test
    void test_validate_combine_both_rules() {
        promotionRules.addItemToCart(new Item("003", KIDS_T_SHIRT, new BigDecimal("19.95")));
        promotionRules.addItemToCart(new Item("001", TRAVEL_CARD_HOLDER, new BigDecimal("9.25")));
        promotionRules.addItemToCart(new Item("001", TRAVEL_CARD_HOLDER, new BigDecimal("9.25")));
        promotionRules.addItemToCart(new Item("002", PERSONALISED_CUFFLINKS, new BigDecimal("45.00")));

        promotionRules.applyRules();

        var amountGiven = promotionRules.getTotalValueAfterDiscount().setScale(precision, RoundingMode.UP);
        var amountExpected = new BigDecimal("73.76").setScale(precision, RoundingMode.DOWN);

        assertThat(amountGiven, is(amountExpected));
    }

    @Test()
    void test_validate_invalid_product_code() {
        InvalidProductCodeException thrown = assertThrows(
                InvalidProductCodeException.class,
                () -> itemService.addProductsOnCartByListOfCodes(Collections.singletonList("005")),
                "Expected throw a new InvalidProductCodeException()"
        );
        assertTrue(thrown.getMessage().contains(EXCEPTION_MESSAGE));
    }

    private BigDecimal round(final BigDecimal value) {
        return value.setScale(precision, roundingMode);
    }
}
