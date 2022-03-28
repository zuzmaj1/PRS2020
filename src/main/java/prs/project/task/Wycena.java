package prs.project.task;

import lombok.Getter;
import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
public enum Wycena {

    CENY_BEZ_ZMIAN(Arrays.asList(WycenaAkcje.PODAJ_CENE)),
    CENA_ZMIENNA(Arrays.asList(WycenaAkcje.PODAJ_CENE, WycenaAkcje.ZMIEN_CENE)),
    PROMO_CO_10_WYCEN(Arrays.asList(WycenaAkcje.PODAJ_CENE));

    Set<WycenaAkcje> akceptowane;

    Wycena(List<WycenaAkcje> types) {
        this.akceptowane = Sets.newHashSet(types);
    }
}
