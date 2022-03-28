package prs.project.task;

import lombok.Getter;
import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
public enum Zaopatrzenie {

    POJEDYNCZE(Arrays.asList(ZaopatrzenieAkcje.POJEDYNCZE_ZAOPATRZENIE)),
    GRUPOWE(Arrays.asList(ZaopatrzenieAkcje.POJEDYNCZE_ZAOPATRZENIE, ZaopatrzenieAkcje.GRUPOWE_ZAOPATRZENIE));

    Set<ZaopatrzenieAkcje> akceptowane;

    Zaopatrzenie(List<ZaopatrzenieAkcje> types) {
        this.akceptowane = Sets.newHashSet(types);
    }
}
