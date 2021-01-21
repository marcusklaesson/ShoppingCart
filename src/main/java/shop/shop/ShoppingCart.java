package shop.shop;

import shop.command.Command;
import shop.command.CommandManager;
import shop.strategies.DiscountInterface;
import shop.strategies.calculateCheapestItem;
import shop.strategies.calculateTenPercent;
import shop.strategies.calculateThreeForTwo;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCart {

    final ArrayList<ShoppingCartItem> items = new ArrayList<>();
    final ArrayList<DiscountInterface> discountsInterface = new ArrayList<>();
    final CommandManager commandManager;
    BigDecimal bestDiscount = BigDecimal.ZERO;

    public ShoppingCart(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void addCartItem(ShoppingCartItem item) {
        items.add(item);
        Command undoCommand = new Command() {
            @Override
            public void execute() {
                items.remove(item);
            }

            @Override
            public void redo() {
                items.add(item);
            }
        };
        commandManager.addToUndo(undoCommand);

    }

    public void removeCartItem(ShoppingCartItem item) {
        items.remove(item);
        Command addCommand = new Command() {
            @Override
            public void execute() {
                items.add(item);
            }

            @Override
            public void redo() {
                items.remove(item);
            }
        };
        commandManager.addToUndo(addCommand);
    }

    public BigDecimal calculatePrice() {
        var sum = BigDecimal.ZERO;
        bestDiscount = BigDecimal.ZERO;

        for (var item : items) {
            sum = item.itemCost().multiply(BigDecimal.valueOf(item.quantity())).add(sum);
        }
        for (var discount : discountsInterface) {
            var sumDiscount = discount.returnDiscount(items);
            if (sum.subtract(sumDiscount).intValue() > bestDiscount.intValue()) {
                bestDiscount = sum.subtract(sumDiscount);
            }
        }
        return sum.subtract(bestDiscount);
    }

    public void addDiscount(DiscountInterface discountInterface) {
        discountsInterface.add(discountInterface);
    }

    public String receipt() {
        String line = "----------------------------------------------------\n";
        StringBuilder sb = new StringBuilder();
        sb.append(line);
        var list = items.stream()
                .sorted(Comparator.comparing(item -> item.product().name()))
                .collect(Collectors.toList());
        for (var each : list) {
            sb.append(String.format("%-24s % 7.2f, Quantity: %d%n\n", each.product().name(), each.itemCost(), each.quantity()));
        }
        sb.append(line);
        sb.append(String.format("%24s% 8.2f", "DISCOUNT:", bestDiscount.multiply(BigDecimal.valueOf(-1))));
        sb.append(String.format("\n%24s% 8.2f", "TOTAL:", calculatePrice()));
        return sb.toString();
    }

    public void undo() {
        int index = commandManager.getUndoList().size() - 1;
        commandManager.getUndoList().get(index).execute();
        commandManager.getRedoList().push(commandManager.getUndoList().peek());
        commandManager.getUndoList().pop();
    }

    public void redo() {
        if (commandManager.getRedoList().size() > 0) {
            commandManager.getRedoList().get(0).redo();
        }
    }


    public static void main(String[] args) {
        CommandManager manager = new CommandManager();
        ShoppingCart cart = new ShoppingCart(manager);

        ShoppingCartItem item1 = new ShoppingCartItem(new Product("Jeans"), 1000, 1, cart.commandManager);
        ShoppingCartItem item2 = new ShoppingCartItem(new Product("T-shirt"), 200, 1, cart.commandManager);
        ShoppingCartItem item3 = new ShoppingCartItem(new Product("Underwear"), 100, 1, cart.commandManager);
        ShoppingCartItem item4 = new ShoppingCartItem(new Product("Socks"), 50, 1, cart.commandManager);

        cart.addCartItem(item1);
        cart.addCartItem(item2);
        cart.addCartItem(item3);
        cart.addCartItem(item4);

        //cart.removeCartItem(item1);


        cart.addDiscount(new calculateCheapestItem());
        //cart.addDiscount(new calculateTenPercent());
        //cart.addDiscount(new calculateThreeForTwo());

        System.out.println(cart.receipt());

        // cart.undo();
        //System.out.println(cart.receipt());
        //cart.redo();
        //System.out.println(cart.receipt());
    }


}