package prs.project.task;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Sets;

@Getter
public enum Zaopatrzenie {

    POJEDYNCZE (Arrays.asList(ZaopatrzenieAkcje.POJEDYNCZE_ZAOPATRZENIE)),
    GRUPOWE (Arrays.asList(ZaopatrzenieAkcje.POJEDYNCZE_ZAOPATRZENIE, ZaopatrzenieAkcje.GRUPOWE_ZAOPATRZENIE));

    Set<ZaopatrzenieAkcje> akceptowane;

    Zaopatrzenie(List<ZaopatrzenieAkcje> types) {
        this.akceptowane = Sets.newHashSet(types);
    }
}
