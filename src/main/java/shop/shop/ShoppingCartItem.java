package shop.shop;

import shop.command.Command;
import shop.command.CommandManager;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class ShoppingCartItem {
    private final BigDecimal itemCost;
    private final Product product;
    private int quantity;
    private final CommandManager commandManager;

    public ShoppingCartItem(@NotNull Product product, double itemCost, int quantity, CommandManager commandManager) {
        this.itemCost = BigDecimal.valueOf(itemCost);
        this.product = product;
        this.quantity = quantity;
        this.commandManager = commandManager;
    }

    public int quantity() {
        return quantity;
    }

    public Product product() {
        return product;
    }

    public BigDecimal itemCost() {
        return itemCost;
    }

    public void setQuantity(int newQuantity) {
        int oldQuantity = quantity;

        Command undoCommand = new Command() {
            @Override
            public void execute() {
                quantity = oldQuantity;
            }

            @Override
            public void redo() {
                quantity = newQuantity;
            }
        };
        this.quantity = newQuantity;
        commandManager.addToUndo(undoCommand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingCartItem lineItem = (ShoppingCartItem) o;

        if (quantity != lineItem.quantity) return false;
        if (!itemCost.equals(lineItem.itemCost)) return false;
        return product.equals(lineItem.product);
    }

    @Override
    public int hashCode() {
        int result = itemCost.hashCode();
        result = 31 * result + product.hashCode();
        result = 31 * result + quantity;
        return result;
    }
}
