package shop.strategies;

import shop.shop.ShoppingCartItem;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface DiscountInterface {

    BigDecimal returnDiscount(ArrayList<ShoppingCartItem> shoppingCart);
}
