package shop.shop;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Product {
    private final UUID id;
    private final String name;

    public Product(@NotNull String name) {
        this.name = name;
        id = java.util.UUID.randomUUID();
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!id.equals(product.id)) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
