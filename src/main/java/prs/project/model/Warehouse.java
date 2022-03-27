package prs.project.model;
import java.util.Arrays;
import java.util.EnumMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Warehouse {

    EnumMap<Product, Long> stanMagazynowy = new EnumMap<>(Product.class);
    EnumMap<Product, Long> ceny = new EnumMap<>(Product.class);

    public Warehouse() {
        Arrays.stream(Product.values()).forEach(product -> stanMagazynowy.put(product, 100L));
        Arrays.stream(Product.values()).forEach(product -> ceny.put(product, 5L));
    }
}
