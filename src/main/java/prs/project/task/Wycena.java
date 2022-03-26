package prs.project.task;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Sets;

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
