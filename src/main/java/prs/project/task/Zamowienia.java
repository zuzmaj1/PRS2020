package prs.project.task;

import org.assertj.core.util.Sets;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.assertj.core.util.Sets;

@Getter
public enum Zamowienia {

    POJEDYNCZE(Arrays.asList(ZamowieniaAkcje.POJEDYNCZE_ZAMOWIENIE)),
    GRUPOWE(Arrays.asList(ZamowieniaAkcje.POJEDYNCZE_ZAMOWIENIE, ZamowieniaAkcje.GRUPOWE_ZAMOWIENIE)),
    REZERWACJE(Arrays.asList(ZamowieniaAkcje.POJEDYNCZE_ZAMOWIENIE, ZamowieniaAkcje.REZERWACJA, ZamowieniaAkcje.ODBIÓR_REZERWACJI));

    Set<ZamowieniaAkcje> akceptowane;

    Zamowienia(List<ZamowieniaAkcje> types) {
        this.akceptowane = Sets.newHashSet(types);
    }
}

