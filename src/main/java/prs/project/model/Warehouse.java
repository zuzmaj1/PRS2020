package prs.project.model;
import java.util.Arrays;
import java.util.EnumMap;
public class Warehouse {

    EnumMap<Product, Long> produktLiczba = new EnumMap<>(Product.class);
    EnumMap<Product, Long> produktCena = new EnumMap<>(Product.class);

    public Warehouse() {
        Arrays.stream(Product.values()).forEach(product -> produktLiczba.put(product, 100L));
        Arrays.stream(Product.values()).forEach(product -> produktCena.put(product, 5L));
    }
}
