package prs.project.task;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Sets;

@Getter
public enum Wydarzenia {

    WYCOFANIE(Arrays.asList(WydarzeniaAkcje.WYCOFANIE, WydarzeniaAkcje.PRZYWROCENIE)),
    INWENTARYZACJA(Arrays.asList(WydarzeniaAkcje.INWENTARYZACJA)),
    RAPORT_SPRZEDAŻY(Arrays.asList(WydarzeniaAkcje.RAPORT_SPRZEDAŻY));

    Set<WydarzeniaAkcje> akceptowane;

    Wydarzenia(List<WydarzeniaAkcje> types) {
        this.akceptowane = Sets.newHashSet(types);
    }
}
