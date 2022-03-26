package prs.project.task;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Sets;

@Getter
public enum Zamowienia {

    POJEDYNCZE (Arrays.asList(ZamowieniaAkcje.POJEDYNCZE_ZAMOWIENIE)),
    GRUPOWE (Arrays.asList(ZamowieniaAkcje.POJEDYNCZE_ZAMOWIENIE, ZamowieniaAkcje.GRUPOWE_ZAMOWIENIE)),
    REZERWACJE (Arrays.asList(ZamowieniaAkcje.POJEDYNCZE_ZAMOWIENIE, ZamowieniaAkcje.REZERWACJA));

    Set<ZamowieniaAkcje> akceptowane;

    Zamowienia(List<ZamowieniaAkcje> types) {
        this.akceptowane = Sets.newHashSet(types);
    }
}

