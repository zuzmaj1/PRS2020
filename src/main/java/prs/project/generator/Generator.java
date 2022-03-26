package prs.project.generator;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

import lombok.AllArgsConstructor;
import prs.project.model.Product;
import prs.project.task.Akcja;
import prs.project.task.Typ;
import prs.project.task.WycenaAkcje;
import prs.project.task.WydarzeniaAkcje;
import prs.project.task.ZamowieniaAkcje;
import prs.project.task.ZaopatrzenieAkcje;

@AllArgsConstructor
public class Generator {

    long numberOfActions;

    public List<Akcja> generate() {
        List<Akcja> actions = new ArrayList<>();

        LongStream.range(0, numberOfActions).forEach(it -> {
            Random r = new Random();
            int actionType = Math.abs(r.nextInt()) % (Typ.values().length*100);
            Typ typ = Typ.values()[actionType/125];
            Akcja action = null;
            switch (typ) {
                case WYCENA: {
                    action = generateWycena();
                    break;
                }
                case WYDARZENIE: {
                    action = generateWydarzenie();
                    break;
                }
                case ZAMOWIENIE: {
                    action = generateZamowienie();
                    break;
                }
                case ZAOPATRZENIE: {
                    action = generateZaopatrzenie();
                    break;
                }
            }
            actions.add(action);
        });

        return actions;
    }

    private Akcja generateZaopatrzenie() {
        Random r = new Random();
        int actionType = Math.abs(r.nextInt()) % ZaopatrzenieAkcje.values().length;
        ZaopatrzenieAkcje event = ZaopatrzenieAkcje.values()[actionType];
        Akcja action;
        switch (event) {
            case GRUPOWE_ZAOPATRZENIE: {
                long ile = Math.abs(r.nextLong()) % 5 + 2;
                EnumMap<Product, Long> productList = new EnumMap<Product, Long>(Product.class);
                LongStream.range(0, ile).forEach(it -> {
                    int productType = Math.abs(r.nextInt()) % Product.values().length;
                    Product product = Product.values()[productType];
                    long liczba = Math.abs(r.nextLong()) % 5 + 1;
                    productList.put(product, liczba);
                });
                action = Akcja.builder()
                        .grupaProduktów(productList)
                        .Typ(event)
                        .build();
                break;
            }
            case POJEDYNCZE_ZAOPATRZENIE: {
                int productType = Math.abs(r.nextInt()) % Product.values().length;
                Product product = Product.values()[productType];
                action = Akcja.builder()
                        .product(product)
                        .liczba(Math.abs(r.nextLong()) % 5 + 1)
                        .Typ(event)
                        .build();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + event);
        }
        return action;
    }

    private Akcja generateZamowienie() {
        Random r = new Random();
        int actionType = Math.abs(r.nextInt()) % ZamowieniaAkcje.values().length;
        ZamowieniaAkcje event = ZamowieniaAkcje.values()[actionType];
        Akcja action;
        switch (event) {
            case GRUPOWE_ZAMOWIENIE: {
                long ile = Math.abs(r.nextLong()) % 5 + 2;
                EnumMap<Product, Long> productList = new EnumMap<Product, Long>(Product.class);
                LongStream.range(0, ile).forEach(it -> {
                    int productType = Math.abs(r.nextInt()) % Product.values().length;
                    Product product = Product.values()[productType];
                    long liczba = Math.abs(r.nextLong()) % 5 + 1;
                    productList.put(product, liczba);
                });
                action = Akcja.builder()
                        .grupaProduktów(productList)
                        .Typ(event)
                        .build();
                break;
            }
            case REZERWACJA:
            case POJEDYNCZE_ZAMOWIENIE: {
                int productType = Math.abs(r.nextInt()) % Product.values().length;
                Product product = Product.values()[productType];
                action = Akcja.builder()
                        .product(product)
                        .liczba(Math.abs(r.nextLong()) % 5 + 1)
                        .Typ(event)
                        .build();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + event);
        }
        return action;
    }

    private Akcja generateWydarzenie() {
        Random r = new Random();
        int actionType = Math.abs(r.nextInt()) % WydarzeniaAkcje.values().length;
        WydarzeniaAkcje event = WydarzeniaAkcje.values()[actionType];
        Akcja action;
        switch (event) {
            case INWENTARYZACJA:
            case RAPORT_SPRZEDAŻY:
                action = Akcja.builder()
                        .Typ(event)
                        .build();
                break;
            case PRZYWROCENIE:
            case WYCOFANIE: {
                int productType = Math.abs(r.nextInt()) % Product.values().length;
                Product product = Product.values()[productType];
                action = Akcja.builder()
                        .product(product)
                        .Typ(event)
                        .build();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + event);
        }
        return action;
    }

    private Akcja generateWycena() {
        Random r = new Random();
        int actionType = Math.abs(r.nextInt()) % WycenaAkcje.values().length;
        WycenaAkcje event = WycenaAkcje.values()[actionType];
        Akcja action;
        switch (event) {
            case ZMIEN_CENE: {
                int productType = Math.abs(r.nextInt()) % Product.values().length;
                Product product = Product.values()[productType];
                action = Akcja.builder()
                        .Typ(event)
                        .product(product)
                        .cena(Math.abs(r.nextLong()) % 100 + 1)
                        .build();
                break;
            }
            case PODAJ_CENE: {
                int productType = Math.abs(r.nextInt()) % Product.values().length;
                Product product = Product.values()[productType];
                action = Akcja.builder()
                        .product(product)
                        .Typ(event)
                        .build();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + event);
        }
        return action;
    }

}
