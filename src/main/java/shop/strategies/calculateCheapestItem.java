package shop.strategies;

import shop.shop.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.ArrayList;

public class calculateCheapestItem implements DiscountInterface {

    public BigDecimal returnDiscount(ArrayList<ShoppingCartItem> shoppingCart) {

        var sum = BigDecimal.ZERO;
        int quantity = 0;
        var cheapest = shoppingCart.get(0).itemCost();

        for (var item : shoppingCart) {
            for (int i = 0; i < item.quantity(); i++) {
                quantity++;

                sum = item.itemCost().add(sum);

                if (item.itemCost().intValue() < cheapest.intValue()) {
                    cheapest = item.itemCost();
                }
            }
        }

        if (quantity > 2) {
            sum = sum.subtract(cheapest);
        }

        return sum;
    }
}
