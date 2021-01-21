package shop.strategies;

import shop.shop.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.ArrayList;

public class calculateTenPercent implements DiscountInterface {

    public BigDecimal returnDiscount(ArrayList<ShoppingCartItem> shoppingCart) {
        var sum = BigDecimal.ZERO;

        for (var item : shoppingCart) {
            sum = item.itemCost().multiply(BigDecimal.valueOf(item.quantity())).add(sum);
        }
        if (sum.compareTo(BigDecimal.valueOf(499)) > 0) {
            sum = sum.multiply(BigDecimal.valueOf(0.9));
        }

        return sum;

    }
}