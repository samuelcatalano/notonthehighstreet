package notonthehighstreet.constants;

import java.math.BigDecimal;

public final class RulesConstants {

    private RulesConstants() {
    }

    public static final Integer    SCALE = 2;
    public static final String     TRAVEL_CARD_CODE = "001";
    public static final BigDecimal DISCOUNTED_PRICE = new BigDecimal("8.5");
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final BigDecimal DISCOUNT_PERCENTAGE = new BigDecimal(10);
    public static final BigDecimal SPEND_OVER_SIXTY = new BigDecimal(60);
}
