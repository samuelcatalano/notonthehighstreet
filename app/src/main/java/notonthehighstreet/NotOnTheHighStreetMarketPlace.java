package notonthehighstreet;

import notonthehighstreet.model.Item;
import notonthehighstreet.service.Checkout;
import notonthehighstreet.service.ItemService;
import notonthehighstreet.service.PromotionalRulesService;

import java.util.Arrays;
import java.util.List;

public class NotOnTheHighStreetMarketPlace {

    private static final ItemService itemService = new ItemService();

    /**
     * Main.
     * @param args the arguments
     */
    public static void main(String[] args) {
        List<Item> cart;

        if (args.length > 0) {
            var codes = args[0].split(",");
            cart = itemService.addProductsOnCartByListOfCodes(Arrays.asList(codes));
        } else {
            cart = itemService.addProductsOnCartByListOfCodes(Arrays.asList("001", "003", "001"));
        }

        var checkout = new Checkout(new PromotionalRulesService());
        for (var item : cart) {
            checkout.scan(item);
        }

        var codes = cart.stream()
                               .map(item -> "".concat(item.getCode()).concat(","))
                               .reduce("", String::concat);

        var productCodes = new StringBuilder(codes);
        productCodes.setCharAt((codes.length() - 1), ' ');

        System.out.println("\n\nTest data");
        System.out.println("----------------------------");
        System.out.println("Basket: " + productCodes);
        System.out.println("Total price expected: £" + checkout.total());
    }
}